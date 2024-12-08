package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.paymenttype.PaymentTypeEnum;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.service.*;
import omsu.softwareengineering.service.discountCacl.DiscountCalculator;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

/**
 * Фасад для управления покупками продуктов, применением скидок и обработкой платежей.
 * <p>
 * Содержит методы для осуществления покупки, возврата продуктов, а также для обработки скидок.
 * </p>
 */
@Slf4j
public class PurchasesFacade implements IFacade {

    private ProductService productApi = IOC.get(ProductService.class);
    private DiscountStrategyService discountStrategyApi = IOC.get(DiscountStrategyService.class);
    private DiscountService discountApi = IOC.get(DiscountService.class);
    private ProductDiscountService productDiscountApi = IOC.get(ProductDiscountService.class);
    private PriceService priceApi = IOC.get(PriceService.class);
    private DiscountCalculator discountCalculator = IOC.get(DiscountCalculator.class);
    private PurchasesService purchasesApi = IOC.get(PurchasesService.class);

    /**
     * Конструктор по умолчанию. Регистрирует данный фасад в IOC.
     */
    public PurchasesFacade() {
        IOC.register(this);
    }

    /**
     * Осуществляет покупку одного продукта.
     *
     * @param productName название продукта, который покупается.
     */
    public void buy(String productName) {
        ProductModel model = productApi.getProductByName(productName);

        ProductDiscountModel productDiscountModel = null;
        DiscountModel discountModel = null;
        DiscountStrategyModel discountStrategyModel = null;
        String method = null;

        try {
            productDiscountModel = productDiscountApi.getProductDiscountByProductID(model.getId());
            discountModel = discountApi.getDiscountByID(productDiscountModel.getDiscountID());
            discountStrategyModel = discountStrategyApi.getStrategyById(discountModel.getDiscountStrategyID());
            method = discountStrategyModel.getMethod();
        } catch (Exception e) {
            log.error("Cant find discount");
        }

        Long price = priceApi.getProductPriceByProductName(productName);
        price = discountCalculator.calc(method, price);

        purchasesApi.buy(model.getId(), "c4e7ed1b-8ab6-44b4-8dd0-1c57f3fad809", PaymentTypeEnum.Cash.name(), price);
    }

    /**
     * Осуществляет покупку нескольких продуктов.
     *
     * @param buyTransfers массив объектов для покупки продуктов.
     */
    public void buyProducts(ProductBuyTransfer ...buyTransfers) {
        var buyer = new ProductBuyer();
        Arrays.stream(buyTransfers).forEach((transfer) -> {
            buyer.buyProduct(transfer, this::buy);
        });
    }

    /**
     * Осуществляет возврат продукта.
     *
     * @param productName название продукта, который нужно вернуть.
     */
    public void returnProduct(String productName) {
        purchasesApi.returnProduct(productName, "c4e7ed1b-8ab6-44b4-8dd0-1c57f3fad809", PaymentTypeEnum.Cash.name());
    }
}
