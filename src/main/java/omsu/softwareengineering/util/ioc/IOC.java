package omsu.softwareengineering.util.ioc;

import java.util.HashMap;

/**
 * Контейнер для внедрения зависимостей (IOC контейнер).
 * Хранит и управляет объектами в приложении.
 */
public class IOC {

    // Коллекция для хранения зарегистрированных объектов
    private static final HashMap<String, Object> container = new HashMap<>();

    /**
     * Регистрирует объект в контейнере с указанным ключом.
     *
     * @param key Ключ для объекта.
     * @param value Объект, который будет зарегистрирован в контейнере.
     * @param <T> Тип объекта.
     */
    public static <T> void register(String key, T value) {
        container.put(key, value);
    }

    /**
     * Регистрирует объект в контейнере, используя его полное имя класса в качестве ключа.
     *
     * @param value Объект, который будет зарегистрирован в контейнере.
     * @param <T> Тип объекта.
     */
    public static <T> void register(T value) {
        register(value.getClass().getCanonicalName(), value);
    }

    /**
     * Получает объект из контейнера по ключу.
     *
     * @param key Ключ объекта.
     * @param <T> Тип возвращаемого объекта.
     * @return Объект, зарегистрированный в контейнере.
     */
    public static <T> T get(String key) {
        return (T) container.get(key);
    }

    /**
     * Получает объект из контейнера по объекту, используя его полное имя класса в качестве ключа.
     *
     * @param obj Объект, который используется для получения зарегистрированного экземпляра.
     * @param <T> Тип возвращаемого объекта.
     * @return Объект, зарегистрированный в контейнере.
     */
    public static <T> T get(T obj) {
        return get(obj.getClass().getCanonicalName());
    }

    /**
     * Получает объект из контейнера по типу класса.
     *
     * @param clazz Класс объекта.
     * @param <T> Тип возвращаемого объекта.
     * @return Объект, зарегистрированный в контейнере.
     */
    public static <T> T get(Class<T> clazz) {
        return get(clazz.getCanonicalName());
    }
}
