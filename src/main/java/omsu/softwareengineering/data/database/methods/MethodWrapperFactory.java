package omsu.softwareengineering.data.database.methods;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Connection;
/**
 * Фабрика для создания оберток методов поиска данных в базе данных.
 * Этот класс предоставляет методы для создания оберток, которые инкапсулируют логику выполнения SQL-запросов
 * для поиска записей в базе данных по различным критериям, таким как ID, поле и значение, а также поиск записей с флагом "deleted".
 */
public class MethodWrapperFactory {

    /**
     * Соединение с базой данных, используемое для выполнения запросов.
     */
    private final Connection connection = IOC.get("connection");

    /**
     * Экстрактор, который извлекает и преобразует результат выборки в объект модели.
     */
    private final Extractor extractor = IOC.get("extractor");

    /**
     * Конструктор фабрики. Регистрирует текущий объект в контейнере IOC.
     */
    public MethodWrapperFactory() {
        IOC.register(this);
    }

    /**
     * Создает обертку для поиска записи по идентификатору в указанной таблице.
     *
     * @param table Имя таблицы, в которой будет выполнен поиск.
     * @param clazz Класс модели, на который будет отображен результат поиска.
     * @param <T> Тип модели, которая будет возвращена в результате поиска.
     * @return Обертка для поиска записи по ID в указанной таблице.
     */
    public <T> FindByIDMethodWrapper<T> findByIDMethodWrapper(String table, Class<T> clazz) {
        return new FindByIDMethodWrapper<T>(table, connection, extractor, clazz);
    }

    /**
     * Выполняет поиск записи в базе данных по указанному полю и значению.
     * Возвращает результат в виде объекта указанного типа модели.
     *
     * @param fieldName Название поля, по которому будет выполнен поиск.
     * @param value Значение, по которому будет выполнен поиск.
     * @param modelClazz Класс модели, на который будет отображен результат поиска.
     * @param table Имя таблицы, в которой будет выполнен поиск.
     * @param <T> Тип модели, которая будет возвращена в результате поиска.
     * @param <A> Тип значения, с которым выполняется поиск.
     * @return Объект модели, найденный по полю и значению.
     */
    public <T, A> T findBy(String fieldName, A value, Class<T> modelClazz, String table) {
        return new FindByMethodWrapper(connection, extractor)
                .findBy(fieldName, value, modelClazz, table);
    }

    /**
     * Выполняет поиск записи в базе данных по указанному полю и значению с учетом флага "deleted".
     * Возвращает результат в виде объекта указанного типа модели.
     *
     * @param fieldName Название поля, по которому будет выполнен поиск.
     * @param value Значение, по которому будет выполнен поиск.
     * @param modelClazz Класс модели, на который будет отображен результат поиска.
     * @param table Имя таблицы, в которой будет выполнен поиск.
     * @param <T> Тип модели, которая будет возвращена в результате поиска.
     * @param <A> Тип значения, с которым выполняется поиск.
     * @return Объект модели, найденный по полю и значению, с учетом флага "deleted".
     */
    public <T, A> T findByDisableable(String fieldName, A value, Class<T> modelClazz, String table) {
        return new FindByDisableableMethodWrapper(connection, extractor)
                .findBy(fieldName, value, modelClazz, table);
    }
}
