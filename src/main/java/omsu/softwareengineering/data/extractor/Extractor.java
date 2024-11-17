package omsu.softwareengineering.data.extractor;

import omsu.softwareengineering.util.generation.IAbstractFactory;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Extractor {
    private final IAbstractFactory fac;

    public Extractor() {
        this.fac = IOC.get("extractorCommandsFactory");
    }

    public <T> T one(Class<T> clazz, PreparedStatement stmt) throws SQLException {
        ResultSet set = stmt.executeQuery();
        if (!set.next()) {
            throw new SQLException();
        }
        return fac.create(clazz).orElseThrow(SQLException::new);
    }
}
