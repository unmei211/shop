package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link ProductDiscountModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link ProductDiscountModel}, который представляет собой
 * информацию о связи продукта и скидки.</p>
 */
public class ProductDiscountExtractorCommand implements IExtractorCommand<ProductDiscountModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link ProductDiscountModel}.
     *
     * @return {@link ProductDiscountModel} с данными из {@link ResultSet}.
     */
    @Override
    public ProductDiscountModel execute() {
        try {
            String id = resultSet.getString("id");
            String productID = resultSet.getString("product_id");
            String discountID = resultSet.getString("discount_id");
            resultSet.next();
            return new ProductDiscountModel(id, productID, discountID);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link ProductDiscountModel}.
     * @return {@link ProductDiscountModel} с извлеченными данными.
     */
    @Override
    public ProductDiscountModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
