package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.service.DiscountService;
import omsu.softwareengineering.service.DiscountStrategyService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class DiscountFacade implements IFacade {
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);

    public DiscountFacade() {
        IOC.register(this);
    }

    public void initDiscount(String strategyMethodName, String description) {
        DiscountStrategyModel discountStrategyModel = discountStrategyApi.getStrategyByName(strategyMethodName);
        discountApi.initDiscount(
                DiscountModel.builder()
                        .discountStrategyID(discountStrategyModel.getId())
                        .description(description)
                        .build()
        );
    }
}
