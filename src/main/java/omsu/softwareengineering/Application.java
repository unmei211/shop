package omsu.softwareengineering;

import omsu.softwareengineering.data.database.IConnectionFactory;
import omsu.softwareengineering.data.database.IConnectionFactoryException;
import omsu.softwareengineering.data.extractor.Extractor;
import omsu.softwareengineering.data.extractor.commands.ExtractorCommandsFactory;
import omsu.softwareengineering.data.repository.category.CategoryRepository;
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

    private void iocConfiguration() {
        final ExtractorCommandsFactory extractorCommandsFactory = new ExtractorCommandsFactory();

        final Extractor extractor = new Extractor();

        final Connection connection = this.connection;
        IOC.register("connection", connection);

        final CategoryRepository repository = new CategoryRepository();
    }

    public void run() {
        try (Connection connection = buildConnection(new IConnectionFactory())) {
            this.connection = connection;
            iocConfiguration();
            var rep = IOC.<CategoryRepository>get("categoryRepository");
        } catch (IConnectionFactoryException | SQLException e) {
            return;
        }
    }
}
