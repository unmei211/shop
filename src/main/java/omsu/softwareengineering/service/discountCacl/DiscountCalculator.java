package omsu.softwareengineering.service.discountCacl;

import omsu.softwareengineering.model.discountstrategy.DiscountStrategyEnum;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.HashMap;
import java.util.function.Function;

public class DiscountCalculator {
    private final HashMap<String, Function<Long, Long>> discountMap = new HashMap<>();

    public DiscountCalculator() {
        discountMap.put(null, (price) -> price);
        discountMap.put(DiscountStrategyEnum.RandomRange.name(), (price) -> (long) (price * 0.8f));
        discountMap.put(DiscountStrategyEnum.Percentage.name(), (price) -> (long) (price * 0.9f));
        discountMap.put(DiscountStrategyEnum.Quantitative.name(), (price) -> (long) (price * 0.6f));

        IOC.register(this);
    }

    public Long calc(String method, Long price) {
        return discountMap.get(method).apply(price);
    }
}
