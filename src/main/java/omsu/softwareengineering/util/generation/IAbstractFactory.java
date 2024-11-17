package omsu.softwareengineering.util.generation;

import java.util.Optional;

public interface IAbstractFactory {
    <T> Optional<T> create(Class<T> clazz);
}
