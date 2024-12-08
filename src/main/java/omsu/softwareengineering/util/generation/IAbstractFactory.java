package omsu.softwareengineering.util.generation;

import java.util.Optional;

/**
 * Интерфейс для фабрики, которая создает объекты на основе предоставленного типа.
 *
 * @param <T> Тип объекта, который требуется создать.
 * @param <D> Тип создаваемого объекта.
 */
public interface IAbstractFactory {

    /**
     * Создает объект указанного типа.
     *
     * @param clazz Класс, объект которого требуется создать.
     * @param <T> Тип объекта, который требуется создать.
     * @param <D> Тип создаваемого объекта.
     * @return Опциональный объект, может быть пустым, если создание не удалось.
     */
    <T, D> Optional<D> create(Class<T> clazz);
}
