package omsu.softwareengineering.data.database;

import omsu.softwareengineering.util.generation.IFactory;

import java.sql.Connection;
import java.util.Optional;

public class IConnectionFactory implements IFactory<Connection> {
    @Override
    public Optional<Connection> create() {
        final PostgresConnection conn = new PostgresConnection(
                "jdbc:postgresql://localhost:5466/shop_lab",
                "admin",
                "admin"
        );
        return conn.getConnection();
    }
}
