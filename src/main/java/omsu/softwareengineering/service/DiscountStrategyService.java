package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyEnum;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

/**
 * Сервис для работы со стратегиями скидок.
 * Обеспечивает операции с различными стратегиями скидок, включая их вставку и поиск.
 */
@Slf4j
public class DiscountStrategyService {

    // Репозиторий для работы со стратегиями скидок
    private final DiscountStrategyRepository discountStrategyRepository =
            IOC.get(DiscountStrategyRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей и инициализирует стандартные стратегии скидок.
     */
    public DiscountStrategyService() {
        IOC.register(this);
        try {
            // Вставка стандартных стратегий скидок в базу
            Arrays.stream(DiscountStrategyEnum.values()).forEach(discountStrategyEnum -> {
                String methodName = discountStrategyEnum.name();
                insertStrategy(DiscountStrategyModel.builder().method(methodName).description(methodName).build());
            });
        } catch (InsertException e) {
            log.info("Cannot insert strategy");
        }
    }

    /**
     * Получает стратегию скидки по её имени.
     *
     * @param methodName Имя метода стратегии скидки.
     * @return Модель стратегии скидки или null, если стратегия не найдена.
     */
    public DiscountStrategyModel getStrategyByName(String methodName) {
        try {
            return discountStrategyRepository.findByMethod(methodName);  // Поиск стратегии по методу
        } catch (FindException e) {
            log.info("Cannot find strategy by method name");
            return null;
        }
    }

    /**
     * Получает стратегию скидки по её ID.
     *
     * @param id Идентификатор стратегии скидки.
     * @return Модель стратегии скидки или null, если стратегия не найдена.
     */
    public DiscountStrategyModel getStrategyById(String id) {
        try {
            return discountStrategyRepository.findByID(id);  // Поиск стратегии по ID
        } catch (FindException e) {
            log.info("Cannot find strategy by id");
            return null;
        }
    }

    /**
     * Вставляет новую стратегию скидки в репозиторий.
     *
     * @param discountStrategyModel Модель стратегии скидки.
     */
    public void insertStrategy(DiscountStrategyModel discountStrategyModel) {
        try {
            discountStrategyRepository.insert(discountStrategyModel);  // Вставка стратегии в репозиторий
        } catch (FindException e) {
            log.error("insert strategy error {}", e.getMessage());
        }
    }

    /**
     * Получает стратегию скидки по её ID.
     *
     * @param id Идентификатор стратегии скидки.
     * @return Модель стратегии скидки или null, если стратегия не найдена.
     */
    public DiscountStrategyModel getProductByID(final String id) {
        DiscountStrategyModel discountStrategyModel = null;
        try {
            discountStrategyModel = discountStrategyRepository.findByID(id);  // Поиск стратегии по ID
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getDiscountStrategyByID: " + e.getMessage());
        }
        return discountStrategyModel;
    }

    /**
     * Получает стратегию скидки по методу (названию).
     *
     * @param method Метод стратегии скидки.
     * @return Модель стратегии скидки или null, если стратегия не найдена.
     */
    public DiscountStrategyModel getDiscountStrategyModelByMethod(final String method) {
        try {
            DiscountStrategyModel model = discountStrategyRepository.findByMethod(method);  // Поиск стратегии по методу
            log.info("getDiscountStrategyModelByMethod: {}", method);
            return model;
        } catch (FindException e) {
            log.info("Can't getDiscountStrategyModelByMethod: {}", e.getMessage());
            return null;
        }
    }
}
