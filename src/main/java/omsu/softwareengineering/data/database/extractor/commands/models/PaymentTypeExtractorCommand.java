package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link PaymentTypeModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link PaymentTypeModel}, который представляет собой
 * тип оплаты в базе данных.</p>
 */
public class PaymentTypeExtractorCommand implements IExtractorCommand<PaymentTypeModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link PaymentTypeModel}.
     *
     * @return {@link PaymentTypeModel} с данными из {@link ResultSet}.
     */
    @Override
    public PaymentTypeModel execute() {
        try {
            String id = resultSet.getString("id");
            String type = resultSet.getString("type");
            resultSet.next();
            return new PaymentTypeModel(id, type);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link PaymentTypeModel}.
     * @return {@link PaymentTypeModel} с извлеченными данными.
     */
    @Override
    public PaymentTypeModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
