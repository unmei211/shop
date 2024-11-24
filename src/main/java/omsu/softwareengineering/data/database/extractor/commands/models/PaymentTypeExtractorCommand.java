package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentTypeExtractorCommand implements IExtractorCommand<PaymentTypeModel> {
    private ResultSet resultSet;

    @Override
    public PaymentTypeModel execute() {
        try {
            String id = resultSet.getString("id");
            String type = resultSet.getString("type");
            resultSet.next();
            return new PaymentTypeModel(id, type);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public PaymentTypeModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
