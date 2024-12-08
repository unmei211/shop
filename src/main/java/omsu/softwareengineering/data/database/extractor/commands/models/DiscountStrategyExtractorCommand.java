package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link DiscountStrategyModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link DiscountStrategyModel}, который представляет собой
 * стратегию скидки в базе данных.</p>
 */
public class DiscountStrategyExtractorCommand implements IExtractorCommand<DiscountStrategyModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link DiscountStrategyModel}.
     *
     * @return {@link DiscountStrategyModel} с данными из {@link ResultSet}.
     */
    @Override
    public DiscountStrategyModel execute() {
        try {
            String id = resultSet.getString("id");
            String description = resultSet.getString("description");
            String method = resultSet.getString("method");
            resultSet.next();
            return new DiscountStrategyModel(id, description, method);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link DiscountStrategyModel}.
     * @return {@link DiscountStrategyModel} с извлеченными данными.
     */
    @Override
    public DiscountStrategyModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
