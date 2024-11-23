package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.price.PriceModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PriceExtractorCommand implements IExtractorCommand<PriceModel> {
    private ResultSet resultSet;

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
            return null;
        }
    }

    @Override
    public PriceModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
