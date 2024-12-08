package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.product.ProductModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link ProductModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link ProductModel}, который представляет собой информацию
 * о продукте, включая его ID, количество, категорию и название.</p>
 */
public class ProductExtractorCommand implements IExtractorCommand<ProductModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link ProductModel}.
     *
     * @return {@link ProductModel} с данными из {@link ResultSet}.
     */
    @Override
    public ProductModel execute() {
        try {
            String id = resultSet.getString("id");
            Long amount = resultSet.getLong("amount");
            String categoryID = resultSet.getString("category_id");
            String name = resultSet.getString("name");
            Boolean deleted = resultSet.getBoolean("deleted");
            resultSet.next();
            return new ProductModel(id, amount, categoryID, name, deleted);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link ProductModel}.
     * @return {@link ProductModel} с извлеченными данными.
     */
    @Override
    public ProductModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
