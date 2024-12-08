package omsu.softwareengineering.util.generation;

import java.util.Optional;

/**
 * Интерфейс для фабрики объектов.
 *
 * @param <T> Тип объектов, которые фабрика будет создавать.
 */
public interface IFactory<T> {

    /**
     * Создаёт объект типа {@code T}.
     *
     * @return Обёртка {@link Optional} с объектом типа {@code T}, если создание прошло успешно.
     */
    Optional<T> create();
}
