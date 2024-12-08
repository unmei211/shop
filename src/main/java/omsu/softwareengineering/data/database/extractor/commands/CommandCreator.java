package omsu.softwareengineering.data.database.extractor.commands;

/**
 * Интерфейс, который описывает метод для создания команд извлечения данных.
 * Каждая команда должна реализовать этот интерфейс для создания экземпляров команд,
 * которые будут использоваться в процессе извлечения данных из базы данных.
 *
 * @param <T> Тип команды, которую должен создавать реализующий класс.
 */
public interface CommandCreator<T> {

    /**
     * Создает и возвращает новый экземпляр команды.
     *
     * @return Новый экземпляр команды.
     */
    T create();
}
