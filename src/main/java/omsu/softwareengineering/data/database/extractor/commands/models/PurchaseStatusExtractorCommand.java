package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link PurchaseStatusModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link PurchaseStatusModel}, который представляет собой информацию
 * о статусе покупки, включая ID и описание статуса.</p>
 */
public class PurchaseStatusExtractorCommand implements IExtractorCommand<PurchaseStatusModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link PurchaseStatusModel}.
     *
     * @return {@link PurchaseStatusModel} с данными из {@link ResultSet}.
     */
    @Override
    public PurchaseStatusModel execute() {
        try {
            String id = resultSet.getString("id");
            String status = resultSet.getString("status");
            resultSet.next();
            return new PurchaseStatusModel(id, status);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link PurchaseStatusModel}.
     * @return {@link PurchaseStatusModel} с извлеченными данными.
     */
    @Override
    public PurchaseStatusModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
