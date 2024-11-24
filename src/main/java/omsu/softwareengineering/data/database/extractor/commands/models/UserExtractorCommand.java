package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.model.user.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractorCommand implements IExtractorCommand<UserModel> {
    private ResultSet resultSet;

    @Override
    public UserModel execute() {
        try {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            resultSet.next();
            return new UserModel(id, name, email);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public UserModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
