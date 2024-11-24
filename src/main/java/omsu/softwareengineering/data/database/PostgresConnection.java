package omsu.softwareengineering.data.database;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class PostgresConnection implements IConnector {
    private Connection pool;
    private final String url;
    private final String user;
    private final String password;

    public PostgresConnection(final String url, final String user, final String password) {
        this.url = url;
        this.password = password;
        this.user = user;
    }

    @Override
    public Optional<Connection> getConnection() {
        if (pool != null) {
            return Optional.of(pool);
        }
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            this.pool = connection;
            log.info("Connected to database");
            return Optional.of(connection);
        } catch (SQLException ex) {
            System.out.println("Failed Postgres Connect");
            return Optional.empty();
        }
    }
}
