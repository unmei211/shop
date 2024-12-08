package omsu.softwareengineering.validation.fields;

import java.util.Arrays;

/**
 * Класс {@code NullValidate} предоставляет статический метод для проверки объектов на {@code null}.
 * Если хотя бы один из переданных объектов является {@code null}, то выбрасывается переданное исключение.
 */
public class NullValidate {

    /**
     * Проверяет переданные объекты на {@code null}. Если хотя бы один объект равен {@code null},
     * выбрасывает переданное исключение.
     *
     * @param e исключение, которое будет выброшено, если хотя бы один из объектов равен {@code null}.
     * @param objects объекты, которые нужно проверить на {@code null}.
     * @throws RuntimeException если хотя бы один из объектов равен {@code null}.
     */
    public static void validOrThrow(RuntimeException e, Object... objects) {
        Arrays.stream(objects).toList().forEach((obj) -> {
            if (obj == null) {
                throw e;
            }
        });
    }
}
