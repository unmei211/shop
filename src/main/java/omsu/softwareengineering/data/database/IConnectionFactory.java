package omsu.softwareengineering.data.database;

import omsu.softwareengineering.util.generation.IFactory;

import java.sql.Connection;
import java.util.Optional;

/**
 * Фабрика для создания подключений к базе данных.
 * Реализует интерфейс {@link IFactory}, создавая подключения для PostgreSQL.
 */
public class IConnectionFactory implements IFactory<Connection> {

    /**
     * Создаёт подключение к базе данных PostgreSQL.
     *
     * @return Optional с объектом соединения, если подключение успешно, или пустое значение в случае ошибки.
     */
    @Override
    public Optional<Connection> create() {
        // Создание объекта соединения с параметрами подключения
        final PostgresConnection conn = new PostgresConnection(
                "jdbc:postgresql://localhost:5466/shop_lab",  // URL базы данных
                "admin",                                     // Имя пользователя
                "admin"                                      // Пароль
        );

        // Возвращаем соединение через Optional
        return conn.getConnection();
    }
}
