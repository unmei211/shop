package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.service.CategoryService;
import omsu.softwareengineering.service.DiscountService;
import omsu.softwareengineering.service.DiscountStrategyService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class ProductDiscountFacade implements IFacade {
    private ProductService productApi = IOC.get(ProductService.class);
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);

    public ProductDiscountFacade() {
        IOC.register(this);
    }

    public void addDiscountForProduct(String productName, String strategyMethodName) {
        DiscountStrategyModel discountStrategyModel = discountStrategyApi.getStrategyByName(strategyMethodName);
        DiscountModel discountModel = discountApi.getDiscountByStrategyID(discountStrategyModel.getId());
        discountApi.initDiscount(DiscountModel.builder()
                ..build());
    }
}
