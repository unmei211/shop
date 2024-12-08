package omsu.softwareengineering.data.database;

import java.sql.Connection;
import java.util.Optional;

/**
 * Интерфейс для получения соединения с базой данных.
 * Классы, реализующие этот интерфейс, предоставляют способ получения активного соединения.
 */
public interface IConnector {

    /**
     * Метод для получения соединения с базой данных.
     *
     * @return Объект {@link Optional<Connection>} с соединением, если оно успешно получено,
     *         или пустое {@link Optional}, если соединение не удалось получить.
     */
    Optional<Connection> getConnection();
}
