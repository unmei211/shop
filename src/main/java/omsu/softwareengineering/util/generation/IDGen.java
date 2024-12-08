package omsu.softwareengineering.util.generation;

import java.util.UUID;

/**
 * Утилитный класс для генерации уникальных идентификаторов.
 */
public class IDGen {

    /**
     * Генерирует уникальный идентификатор в виде строки.
     *
     * @return Строка, представляющая уникальный идентификатор.
     */
    public static String gen() {
        return UUID.randomUUID().toString();
    }
}
