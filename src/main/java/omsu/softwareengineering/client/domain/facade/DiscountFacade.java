package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.service.DiscountService;
import omsu.softwareengineering.service.DiscountStrategyService;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Фасад для работы со скидками.
 * <p>Обеспечивает упрощённый интерфейс для управления стратегиями и моделями скидок.</p>
 */
@Slf4j
public class DiscountFacade implements IFacade {
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);

    /**
     * Конструктор фасада.
     * <p>Регистрирует экземпляр фасада в IOC-контейнере.</p>
     */
    public DiscountFacade() {
        IOC.register(this);
    }

    /**
     * Инициализирует новую скидку на основе указанной стратегии.
     *
     * @param strategyMethodName название метода стратегии скидки (например, "Percentage", "RandomRange").
     * @param description        описание скидки.
     * @throws IllegalArgumentException если указанная стратегия не найдена.
     */
    public void initDiscount(String strategyMethodName, String description) {
        // Получение стратегии скидки по названию
        DiscountStrategyModel discountStrategyModel = discountStrategyApi.getStrategyByName(strategyMethodName);
        if (discountStrategyModel == null) {
            throw new IllegalArgumentException("Strategy not found: " + strategyMethodName);
        }

        // Инициализация скидки с использованием полученной стратегии
        discountApi.initDiscount(
                DiscountModel.builder()
                        .discountStrategyID(discountStrategyModel.getId())
                        .description(description)
                        .build()
        );

        log.info("Initialized discount with strategy: {}, description: {}", strategyMethodName, description);
    }
}
