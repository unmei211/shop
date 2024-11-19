package omsu.softwareengineering.data.database.extractor;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.util.generation.IAbstractFactory;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Extractor {
    private final IAbstractFactory fac;

    public Extractor() {
        this.fac = IOC.get("extractorCommandsFactory");

        IOC.register("extractor", this);
    }

    public <T> T one(Class<T> clazz, PreparedStatement stmt) throws SQLException {
        ResultSet set = stmt.executeQuery();
        if (!set.next()) {
            throw new SQLException();
        }
        IExtractorCommand<T> command = fac.<T, IExtractorCommand<T>>create(clazz).orElseThrow(SQLException::new);
        return command.setSetExecute(set);
    }
}
