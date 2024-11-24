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

    public <T, A> T findBy(String fieldName, A value, Class<T> modelClazz, String table) {
        return new FindByMethodWrapper(connection, extractor)
                .findBy(fieldName, value, modelClazz, table);
    }

    public <T, A> T findByDisableable(String fieldName, A value, Class<T> modelClazz, String table) {
        return new FindByDisableableMethodWrapper(connection, extractor)
                .findBy(fieldName, value, modelClazz, table);
    }
}
