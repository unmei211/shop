package omsu.softwareengineering.data.database.extractor.commands;

import omsu.softwareengineering.util.behavior.IResultingCommand;

import java.sql.ResultSet;

/**
 * Интерфейс для команд, предназначенных для извлечения данных из {@link ResultSet} и преобразования их в модель.
 * <p>Каждая команда реализует метод {@link #setSetExecute(ResultSet)}, который настраивает команду для выполнения
 * с конкретным {@link ResultSet}, а также метод {@link #execute()}, который выполняет команду и возвращает результат.</p>
 *
 * @param <T> Тип модели, который команда должна извлечь из {@link ResultSet}.
 * @see IResultingCommand
 */
public interface IExtractorCommand<T> extends IResultingCommand<T> {

    /**
     * Выполняет команду и возвращает результат в виде модели {@link T}.
     *
     * @return Извлеченная модель.
     */
    @Override
    T execute();

    /**
     * Настраивает команду для извлечения данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные.
     * @return Модель {@link T}, которая будет заполнена данными из {@link ResultSet}.
     */
    T setSetExecute(final ResultSet resultSet);
}
