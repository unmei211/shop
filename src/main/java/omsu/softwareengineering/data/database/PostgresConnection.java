package omsu.softwareengineering.data.database;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс для подключения к базе данных PostgreSQL.
 * Реализует интерфейс {@link IConnector} для получения соединений с PostgreSQL базой данных.
 */
@Slf4j
public class PostgresConnection implements IConnector {

    private Connection pool;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Конструктор для инициализации параметров подключения к базе данных.
     *
     * @param url      URL базы данных.
     * @param user     Имя пользователя для подключения.
     * @param password Пароль для подключения.
     */
    public PostgresConnection(final String url, final String user, final String password) {
        this.url = url;
        this.password = password;
        this.user = user;
    }

    /**
     * Получение соединения с базой данных.
     * Если соединение уже установлено, оно будет возвращено из пула.
     * В противном случае будет установлено новое соединение с базой данных.
     *
     * @return Объект {@link Optional<Connection>} с активным соединением,
     *         или пустой {@link Optional}, если подключение не удалось установить.
     */
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
            log.error("Failed to connect to PostgreSQL database", ex);
            return Optional.empty();
        }
    }
}
