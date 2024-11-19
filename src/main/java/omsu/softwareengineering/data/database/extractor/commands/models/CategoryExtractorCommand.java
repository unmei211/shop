package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryExtractorCommand implements IExtractorCommand<CategoryModel> {
    private ResultSet resultSet;

    @Override
    public CategoryModel execute() {
        try {
            String categoryID = resultSet.getString("id");
            String name = resultSet.getString("name");
            resultSet.next();
            return new CategoryModel(categoryID, name);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public CategoryModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
