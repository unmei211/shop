package omsu.softwareengineering.data.database.methods;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Connection;

public class MethodWrapperFactory {
    private final Connection connection = IOC.get("connection");
    private final Extractor extractor = IOC.get("extractor");

    public MethodWrapperFactory() {
        IOC.register(this);
    }

    public <T> FindByIDMethodWrapper<T> findByIDMethodWrapper(String table, Class<T> clazz) {
        return new FindByIDMethodWrapper<T>(table, connection, extractor, clazz);
    }
}
