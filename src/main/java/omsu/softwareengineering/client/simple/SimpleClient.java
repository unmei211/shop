package omsu.softwareengineering.client.simple;

import omsu.softwareengineering.client.IClient;
import omsu.softwareengineering.client.domain.facade.PriceFacade;
import omsu.softwareengineering.client.domain.facade.ProductFacade;
import omsu.softwareengineering.client.domain.facade.UserFacade;
import omsu.softwareengineering.service.*;
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
    }

    private void initializeFacades() {
        productFacade = new ProductFacade();
        userFacade = new UserFacade();
        priceFacade = new PriceFacade();
    }

    @Override
    public void handle() {
        initializeFacades();
        productFacade.addProduct("tech", "Keyboard", 200L);
        productFacade.addProduct("tech", "Computer", 200L);

        productFacade.deleteProduct("Keyboard");

        userFacade.addUser("unmei", "unmei@gmail.com");
        userFacade.addUser("feanor", "feanor@gmail.com");

        priceFacade.changePriceByProductName("Computer", 2000L);
        priceFacade.getPriceByProductName("Computer");
    }
}
