package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.service.*;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Фасад для работы с продуктами и скидками.
 * <p>
 * Содержит методы для управления связями между продуктами и скидками.
 * </p>
 */
@Slf4j
public class ProductDiscountFacade implements IFacade {

    private ProductService productApi = IOC.get(ProductService.class);
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);
    private ProductDiscountService productDiscountApi = IOC.get(ProductDiscountService.class);

    /**
     * Конструктор по умолчанию. Регистрирует данный фасад в IOC.
     */
    public ProductDiscountFacade() {
        IOC.register(this);
    }

    /**
     * Добавляет скидку для указанного продукта с использованием стратегии скидки.
     *
     * @param productName        название продукта, к которому необходимо применить скидку.
     * @param strategyMethodName имя метода стратегии скидки, используемого для вычислений.
     */
    public void addDiscountForProduct(String productName, String strategyMethodName) {
        DiscountStrategyModel discountStrategyModel = discountStrategyApi.getStrategyByName(strategyMethodName);
        productDiscountApi.bundleProductViaDiscount(productName, discountStrategyModel.getId());
    }
}
