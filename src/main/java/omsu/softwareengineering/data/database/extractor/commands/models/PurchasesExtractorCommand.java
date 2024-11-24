package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.purchases.PurchasesModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PurchasesExtractorCommand implements IExtractorCommand<PurchasesModel> {
    private ResultSet resultSet;

    @Override
    public PurchasesModel execute() {
        try {
            String purchasesID = resultSet.getString("id");
            String productID = resultSet.getString("product_id");
            String userID = resultSet.getString("user_id");
            String paymentTypeID = resultSet.getString("payment_type_id");
            Timestamp date = resultSet.getTimestamp("date");
            String purchaseStatusID = resultSet.getString("purchase_status_id");
            resultSet.next();
            return new PurchasesModel(purchasesID, productID, userID, paymentTypeID, date, purchaseStatusID);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public PurchasesModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
