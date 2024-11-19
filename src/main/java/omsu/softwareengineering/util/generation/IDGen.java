package omsu.softwareengineering.util.generation;

import java.util.UUID;

public class IDGen {
    public static String gen() {
        return UUID.randomUUID().toString();
    }
}
