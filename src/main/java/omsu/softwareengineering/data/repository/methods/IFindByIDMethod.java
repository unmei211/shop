package omsu.softwareengineering.data.repository.methods;

import omsu.softwareengineering.data.repository.FindException;

/**
 * Интерфейс, который определяет метод для поиска сущности по её идентификатору.
 * <p>Этот интерфейс должен быть реализован для выполнения поиска объекта по его уникальному идентификатору
 * в базе данных или другом хранилище данных.</p>
 *
 * @param <T> Тип объекта, который возвращает метод поиска.
 */
public interface IFindByIDMethod<T> {

    /**
     * Находит сущность по её идентификатору.
     *
     * @param id Идентификатор сущности, по которому осуществляется поиск.
     * @return Найденный объект типа {@link T}.
     * @throws FindException Исключение, если поиск не удался (например, объект с таким ID не найден).
     */
    T findByID(String id) throws FindException;
}
