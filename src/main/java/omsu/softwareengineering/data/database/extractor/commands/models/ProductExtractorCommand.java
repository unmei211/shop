package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.product.ProductModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductExtractorCommand implements IExtractorCommand<ProductModel> {
    private ResultSet resultSet;

    @Override
    public ProductModel execute() {
        try {
            String id = resultSet.getString("id");
            Long amount = resultSet.getLong("amount");
            String categoryID = resultSet.getString("category_id");
            String name = resultSet.getString("name");
            resultSet.next();
            return new ProductModel(id, amount, categoryID, name);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ProductModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
