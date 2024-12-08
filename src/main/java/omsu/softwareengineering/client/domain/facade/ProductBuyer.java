package omsu.softwareengineering.client.domain.facade;

import java.util.function.Consumer;

public class ProductBuyer {
    public void buyProduct(ProductBuyTransfer productBuyTransfer, Consumer<String> buyMethod) {
        for (int i = 0; i < productBuyTransfer.getCount(); i++) {
            buyMethod.accept(productBuyTransfer.getProductName());
        }
    }
}
