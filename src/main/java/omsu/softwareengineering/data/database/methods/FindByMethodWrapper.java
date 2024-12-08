package omsu.softwareengineering.data.database.methods;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Этот класс инкапсулирует логику выполнения запроса к базе данных для поиска записи по заданному полю и значению.
 * Он использует {@code PreparedStatement} для защиты от SQL-инъекций и выполнения параметризованных запросов.
 * Результат извлекается с помощью {@code Extractor}, который отображает результаты на указанный тип модели.
 */
@RequiredArgsConstructor
@Slf4j
public class FindByMethodWrapper {

    /**
     * Соединение с базой данных, используемое для выполнения запроса.
     */
    private final Connection connection;

    /**
     * Экстрактор, который извлекает и преобразует результат выборки в объект указанного типа.
     */
    private final Extractor extractor;

    /**
     * Находит запись в базе данных по заданному полю и значению.
     * Выполняет параметризованный запрос к базе данных и извлекает результат с помощью {@code Extractor}.
     *
     * @param <T> Тип модели, на который нужно отобразить результат поиска.
     * @param <A> Тип значения, с которым выполняется поиск.
     * @param fieldName Название поля, по которому выполняется поиск.
     * @param value Значение, по которому выполняется поиск.
     * @param modelClazz Класс модели, на который будет отображен результат поиска.
     * @param table Название таблицы, в которой выполняется поиск.
     * @return Объект типа {@code T}, представляющий найденную запись.
     * @throws FindException Если произошла ошибка при выполнении запроса или извлечении данных.
     */
    public <T, A> T findBy(
            String fieldName,
            A value,
            Class<T> modelClazz,
            String table
    ) throws FindException {
        final String sql = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1", table, fieldName);

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
