package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.purchases.PurchasesModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link PurchasesModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link PurchasesModel}, который представляет собой информацию
 * о покупке, включая ID покупки, продукт, пользователя, тип оплаты, дату и статус покупки, а также цену.</p>
 */
public class PurchasesExtractorCommand implements IExtractorCommand<PurchasesModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link PurchasesModel}.
     *
     * @return {@link PurchasesModel} с данными из {@link ResultSet}.
     */
    @Override
    public PurchasesModel execute() {
        try {
            String purchasesID = resultSet.getString("id");
            String productID = resultSet.getString("product_id");
            String userID = resultSet.getString("user_id");
            String paymentTypeID = resultSet.getString("payment_type_id");
            Timestamp date = resultSet.getTimestamp("date");
            String purchaseStatusID = resultSet.getString("purchase_status_id");
            Long price = resultSet.getLong("price");
            resultSet.next();
            return new PurchasesModel(purchasesID, productID, userID, paymentTypeID, date, purchaseStatusID, price);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link PurchasesModel}.
     * @return {@link PurchasesModel} с извлеченными данными.
     */
    @Override
    public PurchasesModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
