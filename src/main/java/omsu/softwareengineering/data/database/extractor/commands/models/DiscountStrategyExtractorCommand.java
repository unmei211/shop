package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DiscountStrategyExtractorCommand implements IExtractorCommand<DiscountStrategyModel> {
    private ResultSet resultSet;

    @Override
    public DiscountStrategyModel execute() {
        try {
            String id = resultSet.getString("id");
            String description = resultSet.getString("description");
            String method = resultSet.getString("method");
            resultSet.next();
            return new DiscountStrategyModel(id, description, method);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public DiscountStrategyModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
