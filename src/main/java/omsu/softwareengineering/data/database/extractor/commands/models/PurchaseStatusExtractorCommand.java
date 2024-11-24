package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseStatusExtractorCommand implements IExtractorCommand<PurchaseStatusModel> {
    private ResultSet resultSet;

    @Override
    public PurchaseStatusModel execute() {
        try {
            String id = resultSet.getString("id");
            String status = resultSet.getString("status");
            resultSet.next();
            return new PurchaseStatusModel(id, status);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public PurchaseStatusModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
