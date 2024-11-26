package omsu.softwareengineering;

import omsu.softwareengineering.client.IClient;
import omsu.softwareengineering.data.database.IConnectionFactory;
import omsu.softwareengineering.data.database.IConnectionFactoryException;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.extractor.commands.ExtractorCommandsFactory;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.discount.DiscountRepository;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.data.repository.repositories.paymenttype.PaymentTypeRepository;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.product_discount.ProductDiscountRepository;
import omsu.softwareengineering.data.repository.repositories.purchases.PurchasesRepository;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.data.repository.repositories.user.UserRepository;
import omsu.softwareengineering.service.*;
import omsu.softwareengineering.util.generation.IFactory;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    private Connection connection;

    private Connection buildConnection(IFactory<Connection> connectionFactory)
            throws IConnectionFactoryException {
        return connectionFactory.create().orElseThrow(() -> new IConnectionFactoryException("Can't create connection"));
    }

    private void toolsConfiguration() {
        final ExtractorCommandsFactory extractorCommandsFactory = new ExtractorCommandsFactory();

        final Extractor extractor = new Extractor();

        final Connection connection = this.connection;
        IOC.register("connection", connection);

        final MethodWrapperFactory factory = new MethodWrapperFactory();
    }

    private void repositoryConfiguration() {
        final CategoryRepository categoryRepository = new CategoryRepository();
        final ProductRepository productRepository = new ProductRepository();
        final PriceRepository priceRepository = new PriceRepository();
        final PaymentTypeRepository paymentTypeRepository = new PaymentTypeRepository();
        final UserRepository userRepository = new UserRepository();
        final PurchaseStatusRepository purchaseStatusRepository = new PurchaseStatusRepository();
        final DiscountStrategyRepository discountStrategyRepository = new DiscountStrategyRepository();
        final DiscountRepository discountRepository = new DiscountRepository();
        final ProductDiscountRepository productDiscountRepository = new ProductDiscountRepository();
        final PurchasesRepository purchasesRepository = new PurchasesRepository();
    }

    private void serviceConfiguration() {
        final CategoryService categoryService = new CategoryService();
        final ProductService productService = new ProductService();
        final PriceService priceService = new PriceService();
        final PaymentTypeService paymentTypeService = new PaymentTypeService();
        final UserService userService = new UserService();
        final PurchaseStatusService purchaseStatusService = new PurchaseStatusService();
        final DiscountStrategyService discountStrategyService = new DiscountStrategyService();
        final DiscountService discountService = new DiscountService();
        final ProductDiscountService productDiscountService = new ProductDiscountService();
        final PurchasesService purchasesService = new PurchasesService();
    }

    private void iocConfiguration() {
        toolsConfiguration();

        repositoryConfiguration();

        serviceConfiguration();
    }

    public void run() {
        try (Connection connection = buildConnection(new IConnectionFactory())) {
            this.connection = connection;
            iocConfiguration();
            IOC.<IClient>get("client").connectApi();
            IOC.<IClient>get("client").handle();
        } catch (IConnectionFactoryException | SQLException e) {
            return;
        }
    }
}
