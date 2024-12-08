package omsu.softwareengineering.data.database;

/**
 * Исключение, возникающее при ошибках, связанных с фабрикой соединений.
 * Это исключение является {@link RuntimeException} и используется для обработки
 * ошибок при создании подключений к базе данных.
 */
public class IConnectionFactoryException extends RuntimeException {

    /**
     * Конструктор для создания исключения с сообщением.
     *
     * @param message Сообщение об ошибке, которое будет передано в конструктор родительского класса {@link RuntimeException}.
     */
    public IConnectionFactoryException(String message) {
        super(message);
    }
}
