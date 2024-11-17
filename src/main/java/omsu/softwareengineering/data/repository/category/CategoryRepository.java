package omsu.softwareengineering.data.repository.category;

import omsu.softwareengineering.data.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.table.Tables;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class CategoryRepository {
    private final Connection connection;
    private final Extractor extractor;

    public CategoryRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
    }

    public CategoryModel findById(final String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        final String sql = "SELECT id, name FROM category WHERE id = ?";
        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeQuery();
            return extractor.one(CategoryModel.class, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }
//
//    public CategoryModel findByName(final String name) {
//
//    }
}
