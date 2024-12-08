package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.discount.DiscountRepository;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Supplier;

/**
 * Сервис для работы с скидками.
 * Обеспечивает выполнение операций по добавлению, поиску и инициализации скидок.
 */
@Slf4j
public class DiscountService {

    // Репозиторий для работы с скидками
    private final DiscountRepository discountRepository = IOC.get(DiscountRepository.class);

    // Репозиторий для работы со стратегиями скидок
    private final DiscountStrategyRepository discountStrategyRepository = IOC.get(DiscountStrategyRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public DiscountService() {
        IOC.register(this);
    }

    /**
     * Получает скидку по ID.
     *
     * @param id Идентификатор скидки.
     * @return Модель скидки или null, если скидка не найдена.
     */
    public DiscountModel getDiscountByID(final String id) {
        DiscountModel model = null;
        try {
            model = discountRepository.findByID(id);  // Поиск скидки по ID
        } catch (FindException e) {
            log.error("getDiscountByID: {}", e.getMessage());
        }
        return model;
    }

    /**
     * Получает скидку по ID стратегии скидки.
     *
     * @param strategyID Идентификатор стратегии скидки.
     * @return Модель скидки или null, если скидка не найдена.
     */
    public DiscountModel getDiscountByStrategyID(final String strategyID) {
        try {
            return discountRepository.findByDiscountStrategyID(strategyID);  // Поиск скидки по ID стратегии
        } catch (FindException e) {
            log.info("Can't found strategy via id: {}", strategyID);
            return null;
        }
    }

    /**
     * Инициализирует скидку, если стратегия скидки существует и скидка с такой стратегией ещё не добавлена.
     * Устанавливает актуальные даты начала и окончания скидки и добавляет её в репозиторий.
     *
     * @param discountModel Модель скидки, которую нужно инициализировать.
     */
    public void initDiscount(final DiscountModel discountModel) {
        // Проверяем наличие стратегии скидки
        if (discountStrategyRepository.findByID(discountModel.getDiscountStrategyID()) == null) {
            log.info("Can't found discount strategy");
            return;
        }

        // Проверяем, существует ли скидка с такой стратегией
        if (discountRepository.findByDiscountStrategyID(discountModel.getDiscountStrategyID()) != null) {
            log.info("Discount via this strategy already exists");
            return;
        }

        // Устанавливаем даты начала и окончания скидки
        discountModel.setEnabled(true);
        final Supplier<Timestamp> actualTimestamp = () -> new Timestamp(System.currentTimeMillis());
        discountModel.setEndDate(actualTimestamp.get());
        discountModel.setStartDate(actualTimestamp.get());

        try {
            // Вставляем скидку в репозиторий
            discountRepository.insert(discountModel);
        } catch (InsertException e) {
            log.error("Can't insert discount");
        }
    }
}
