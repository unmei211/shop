package omsu.softwareengineering;

import omsu.softwareengineering.data.database.IConnectionFactory;
import omsu.softwareengineering.data.database.IConnectionFactoryException;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.extractor.commands.ExtractorCommandsFactory;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.category.CategoryRepository;
import omsu.softwareengineering.data.repository.product.ProductRepository;
import omsu.softwareengineering.data.service.CategoryService;
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

    private void serviceConfiguration() {
        final CategoryService categoryService = new CategoryService();

        IOC.get(categoryService);
    }

    private void toolsConfiguration() {
    }

    private void repositoryConfiguration() {
        final CategoryRepository repository = new CategoryRepository();

        final ProductRepository productRepository = new ProductRepository();
    }

    private void iocConfiguration() {
        final ExtractorCommandsFactory extractorCommandsFactory = new ExtractorCommandsFactory();

        final Extractor extractor = new Extractor();

        final Connection connection = this.connection;
        IOC.register("connection", connection);

        final MethodWrapperFactory factory = new MethodWrapperFactory();


        repositoryConfiguration();

        serviceConfiguration();

        toolsConfiguration();
    }

    public void run() {
        try (Connection connection = buildConnection(new IConnectionFactory())) {
            this.connection = connection;
            iocConfiguration();
            IOC.get(CategoryService.class).getCategoryByID("myid");
        } catch (IConnectionFactoryException | SQLException e) {
            return;
        }
    }
}
