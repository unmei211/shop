package omsu.softwareengineering.util.ioc;

import java.util.HashMap;

public class IOC {
    private static final HashMap<String, Object> container = new HashMap<>();

    public static <T> void register(String key, T value) {
        container.put(key, value);
    }

    public static <T> T get(String key) {
        return (T) container.get(key);
    }
}
