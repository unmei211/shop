package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.service.*;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class ProductDiscountFacade implements IFacade {
    private ProductService productApi = IOC.get(ProductService.class);
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);
    private ProductDiscountService productDiscountApi = IOC.get(ProductDiscountService.class);

    public ProductDiscountFacade() {
        IOC.register(this);
    }

    public void addDiscountForProduct(String productName, String strategyMethodName) {
        DiscountStrategyModel discountStrategyModel = discountStrategyApi.getStrategyByName(strategyMethodName);
        productDiscountApi.bundleProductViaDiscount(productName, discountStrategyModel.getId());
    }
}
