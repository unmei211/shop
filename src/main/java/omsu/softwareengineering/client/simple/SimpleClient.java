package omsu.softwareengineering.client.simple;

import omsu.softwareengineering.client.IClient;
import omsu.softwareengineering.client.domain.facade.*;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyEnum;
import omsu.softwareengineering.service.*;
import omsu.softwareengineering.service.discountCacl.DiscountCalculator;
import omsu.softwareengineering.util.ioc.IOC;

public class SimpleClient implements IClient {
    private CategoryService categoryApi;
    private ProductService productApi;
    private PriceService priceApi;
    private PaymentTypeService paymentTypeApi;
    private UserService userApi;
    private PurchaseStatusService purchaseStatusApi;
    private DiscountStrategyService discountStrategyApi;
    private DiscountService discountApi;
    private ProductDiscountService productDiscountApi;
    private PurchasesService purchasesApi;
    private ProductFacade productFacade;
    private UserFacade userFacade;
    private PriceFacade priceFacade;
    private DiscountFacade discountFacade;
    private ProductDiscountFacade productDiscountFacade;
    private PurchasesFacade purchasesFacade;

    @Override
    public void connectApi() {
        categoryApi = IOC.get(CategoryService.class);
        productApi = IOC.get(ProductService.class);
        priceApi = IOC.get(PriceService.class);
        paymentTypeApi = IOC.get(PaymentTypeService.class);
        userApi = IOC.get(UserService.class);
        purchaseStatusApi = IOC.get(PurchaseStatusService.class);
        discountStrategyApi = IOC.get(DiscountStrategyService.class);
        discountApi = IOC.get(DiscountService.class);
        productDiscountApi = IOC.get(ProductDiscountService.class);
        purchasesApi = IOC.get(PurchasesService.class);

    }

    public SimpleClient() {
        IOC.register("client", this);
        new DiscountCalculator();
    }

    private void initializeFacades() {
        productFacade = new ProductFacade();
        userFacade = new UserFacade();
        priceFacade = new PriceFacade();
        discountFacade = new DiscountFacade();
        productDiscountFacade = new ProductDiscountFacade();
        purchasesFacade = new PurchasesFacade();
    }

    @Override
    public void handle() {
        initializeFacades();
        productFacade.addProduct("tech", "Keyboard", 200L);
        productFacade.addProduct("tech", "Computer", 200L);

//        productFacade.deleteProduct("Keyboard");
        productFacade.addProduct("tech", "Keyboard", 200L);

        userFacade.addUser("unmei", "unmei@gmail.com");
        userFacade.addUser("feanor", "feanor@gmail.com");

        priceFacade.changePriceByProductName("Computer", 2000L);
        priceFacade.changePriceByProductName("Keyboard", 1000L);

        priceFacade.getPriceByProductName("Computer");

        discountFacade.initDiscount(DiscountStrategyEnum.Percentage.name(), "Percentage sell");
        discountFacade.initDiscount(DiscountStrategyEnum.RandomRange.name(), "RandomRange sell");
        discountFacade.initDiscount(DiscountStrategyEnum.Quantitative.name(), "Quantitative sell");

        productDiscountFacade.addDiscountForProduct("Keyboard", DiscountStrategyEnum.RandomRange.name());

        purchasesFacade.buy("Keyboard");
    }
}
