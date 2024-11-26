package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.service.PriceService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class PriceFacade implements IFacade {
    private PriceService priceApi = IOC.get(PriceService.class);
    private ProductService productApi = IOC.get(ProductService.class);

    public PriceFacade() {
        IOC.register(this);
    }

    public void changePriceByProductName(String name, Long price) {
        ProductModel productModel = productApi.getProductByName(name);
        priceApi.changePrice(PriceModel.builder().price(price).productID(productModel.getId()).build());
    }

    public Long getPriceByProductName(String name) {
        Long price = priceApi.getProductPriceByProductName(name);
        log.info("getPriceByProductName: {}", price);
        return price;
    }
}
