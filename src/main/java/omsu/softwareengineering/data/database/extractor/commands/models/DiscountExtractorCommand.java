package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.discount.DiscountModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link DiscountModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link DiscountModel}, который представляет собой
 * скидку в базе данных.</p>
 */
public class DiscountExtractorCommand implements IExtractorCommand<DiscountModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link DiscountModel}.
     *
     * @return {@link DiscountModel} с данными из {@link ResultSet}.
     */
    @Override
    public DiscountModel execute() {
        try {
            String id = resultSet.getString("id");
            String description = resultSet.getString("description");
            Timestamp startDate = resultSet.getTimestamp("start_date");
            Timestamp endDate = resultSet.getTimestamp("end_date");
            Boolean enabled = resultSet.getBoolean("enabled");
            String discountStrategyID = resultSet.getString("discount_strategy_id");
            resultSet.next();
            return new DiscountModel(id, description, startDate, endDate, enabled, discountStrategyID);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link DiscountModel}.
     * @return {@link DiscountModel} с извлеченными данными.
     */
    @Override
    public DiscountModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
