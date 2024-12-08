package omsu.softwareengineering.data.database.methods;

import lombok.RequiredArgsConstructor;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Этот класс инкапсулирует логику выполнения запроса к базе данных для поиска записи по уникальному идентификатору.
 * Он использует `PreparedStatement` для защиты от SQL-инъекций и оптимизации работы с запросами.
 * Результат извлекается с помощью `Extractor`, который отображает результаты на указанный тип модели.
 *
 * @param <T> Тип модели, на который нужно отобразить результат поиска.
 */
@RequiredArgsConstructor
public class FindByIDMethodWrapper<T> implements IFindByIDMethod<T> {

    /**
     * Название таблицы, в которой выполняется поиск.
     */
    private final String table;

    /**
     * Соединение с базой данных, используемое для выполнения запроса.
     */
    private final Connection connection;

    /**
     * Экстрактор, который извлекает и преобразует результат выборки в объект типа {@code T}.
     */
    private final Extractor extractor;

    /**
     * Класс модели, на который будет отображен результат поиска.
     */
    private final Class<T> clazz;

    /**
     * Находит запись в базе данных по уникальному идентификатору.
     * Выполняет запрос к базе данных с использованием {@code PreparedStatement} и отображает результат
     * на модель с помощью {@code Extractor}.
     *
     * @param id Уникальный идентификатор записи для поиска.
     * @return Объект типа {@code T}, представляющий найденную запись.
     * @throws FindException Если произошла ошибка при выполнении запроса или извлечении данных.
     */
    @Override
    public T findByID(String id) throws FindException {
        final String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", table);

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeQuery();
            return extractor.one(clazz, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }
}
