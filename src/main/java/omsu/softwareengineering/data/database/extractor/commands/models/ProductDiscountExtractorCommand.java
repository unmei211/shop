package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductDiscountExtractorCommand implements IExtractorCommand<ProductDiscountModel> {
    private ResultSet resultSet;

    @Override
    public ProductDiscountModel execute() {
        try {
            String id = resultSet.getString("id");
            String productID = resultSet.getString("product_id");
            String discountID = resultSet.getString("discount_id");
            resultSet.next();
            return new ProductDiscountModel(id, productID, discountID);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ProductDiscountModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
