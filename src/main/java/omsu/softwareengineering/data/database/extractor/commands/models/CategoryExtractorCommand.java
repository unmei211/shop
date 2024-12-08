package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link CategoryModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link CategoryModel}, который представляет собой
 * категорию в базе данных.</p>
 */
public class CategoryExtractorCommand implements IExtractorCommand<CategoryModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link CategoryModel}.
     *
     * @return {@link CategoryModel} с данными из {@link ResultSet}.
     */
    @Override
    public CategoryModel execute() {
        try {
            String categoryID = resultSet.getString("id");
            String name = resultSet.getString("name");
            resultSet.next();
            return new CategoryModel(categoryID, name);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link CategoryModel}.
     * @return {@link CategoryModel} с извлеченными данными.
     */
    @Override
    public CategoryModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
