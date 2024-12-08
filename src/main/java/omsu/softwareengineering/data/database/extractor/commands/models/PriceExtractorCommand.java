package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.price.PriceModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link PriceModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link PriceModel}, который представляет собой
 * информацию о цене товара в базе данных.</p>
 */
public class PriceExtractorCommand implements IExtractorCommand<PriceModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link PriceModel}.
     *
     * @return {@link PriceModel} с данными из {@link ResultSet}.
     */
    @Override
    public PriceModel execute() {
        try {
            String priceID = resultSet.getString("id");
            Long price = resultSet.getLong("price");
            Timestamp startDate = resultSet.getTimestamp("start_date");
            Timestamp endDate = resultSet.getTimestamp("end_date");
            String productID = resultSet.getString("product_id");
            resultSet.next();
            return new PriceModel(priceID, price, startDate, endDate, productID);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link PriceModel}.
     * @return {@link PriceModel} с извлеченными данными.
     */
    @Override
    public PriceModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
