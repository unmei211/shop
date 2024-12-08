package omsu.softwareengineering.data.database.methods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Этот класс инкапсулирует логику выполнения запроса к базе данных для поиска записей по указанному полю
 * и значению, при этом исключая записи, помеченные как удаленные (т.е. где `deleted = false`).
 * Запросы выполняются с использованием `PreparedStatement` для предотвращения SQL-инъекций и оптимизации запросов.
 * Результат извлекается с помощью `Extractor`, который отображает результаты на соответствующий класс модели.
 */
@RequiredArgsConstructor
@Slf4j
public class FindByDisableableMethodWrapper {

    /**
     * Соединение с базой данных, используемое для выполнения запроса.
     */
    private final Connection connection;

    /**
     * Экстрактор, который отвечает за отображение результата выборки на модель.
     */
    private final Extractor extractor;

    /**
     * Находит запись по заданному полю и значению в таблице, при этом возвращает только те записи,
     * которые не помечены как удаленные (где `deleted = false`).
     *
     * @param <T> Тип модели, на который нужно отобразить результат.
     * @param <A> Тип значения поля для поиска.
     * @param fieldName Название поля в таблице для поиска.
     * @param value Значение поля для поиска.
     * @param modelClazz Класс модели, в который нужно отобразить результат.
     * @param table Название таблицы, в которой выполняется поиск.
     * @return Маппированный результат типа {@code T}, если запись найдена, или {@code null}, если запись не найдена.
     * @throws FindException В случае ошибки при выполнении запроса или извлечении данных.
     */
    public <T, A> T findBy(
            String fieldName,
            A value,
            Class<T> modelClazz,
            String table
    ) throws FindException {
        final String sql = String.format("SELECT * FROM %s WHERE %s = ? AND deleted = false LIMIT 1", table, fieldName);

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setObject(1, value);
            stmt.executeQuery();
            return extractor.one(modelClazz, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }
}
