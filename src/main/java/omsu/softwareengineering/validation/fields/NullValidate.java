package omsu.softwareengineering.validation.fields;

import java.util.Arrays;

public class NullValidate {
    public static void validOrThrow(RuntimeException e, Object... objects) {
        Arrays.stream(objects).toList().forEach((obj) -> {
            if (obj == null) {
                throw e;
            }
        });
    }
}
