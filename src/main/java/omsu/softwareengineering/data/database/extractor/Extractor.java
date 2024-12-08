package omsu.softwareengineering.data.database.extractor;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.util.generation.IAbstractFactory;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, который извлекает данные из базы данных и преобразует их в объекты модели.
 * Использует команды извлечения, которые создаются через фабрику команд.
 */
public class Extractor {
    /**
     * Фабрика команд для извлечения данных.
     */
    private final IAbstractFactory fac;

    /**
     * Конструктор, который инициализирует фабрику команд извлечения и регистрирует текущий объект в контейнере IOC.
     */
    public Extractor() {
        this.fac = IOC.get("extractorCommandsFactory");

        IOC.register("extractor", this);
    }

    /**
     * Извлекает одну запись из результата SQL-запроса и преобразует ее в объект модели.
     *
     * @param clazz Класс модели, в объект которой будет преобразована извлеченная запись.
     * @param stmt Подготовленный SQL-запрос для выполнения.
     * @param <T> Тип объекта модели, который будет возвращен.
     * @return Объект модели, который соответствует извлеченной записи.
     * @throws SQLException Если не удается выполнить запрос или извлечь данные.
     */
    public <T> T one(Class<T> clazz, PreparedStatement stmt) throws SQLException {
        ResultSet set = stmt.executeQuery();
        if (!set.next()) {
            throw new SQLException("No data found.");
        }
        IExtractorCommand<T> command = fac.<T, IExtractorCommand<T>>create(clazz)
                .orElseThrow(() -> new SQLException("Command creation failed."));
        return command.setSetExecute(set);
    }
}

