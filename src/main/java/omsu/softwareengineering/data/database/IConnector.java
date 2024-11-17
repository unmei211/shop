package omsu.softwareengineering.data.database;

import java.sql.Connection;
import java.util.Optional;

public interface IConnector {
    Optional<Connection> getConnection();
}
