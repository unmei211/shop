package omsu.softwareengineering.util.generation;

import java.util.Optional;

public interface IAbstractFactory {
    <T, D> Optional<D> create(Class<T> clazz);
}
