package omsu.softwareengineering.util.ioc;

import java.util.HashMap;

public class IOC {
    private static final HashMap<String, Object> container = new HashMap<>();

    public static <T> void register(String key, T value) {
        container.put(key, value);
    }

    public static <T> void register(T value) {
        register(value.getClass().getCanonicalName(), value);
    }

    public static <T> T get(String key) {
        return (T) container.get(key);
    }

    public static <T> T get(T obj) {
        return get(obj.getClass().getCanonicalName());
    }

    public static <T> T get(Class<T> clazz) {
        return get(clazz.getCanonicalName());
    }
}
