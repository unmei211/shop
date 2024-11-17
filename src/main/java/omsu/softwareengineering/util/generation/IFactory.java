package omsu.softwareengineering.util.generation;

import java.util.Optional;

public interface IFactory<T> {
    Optional<T> create();
}
