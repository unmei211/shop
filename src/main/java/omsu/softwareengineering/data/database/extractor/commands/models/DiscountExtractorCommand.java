package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.product.ProductModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DiscountExtractorCommand implements IExtractorCommand<DiscountModel> {
    private ResultSet resultSet;

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
            return null;
        }
    }

    @Override
    public DiscountModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
