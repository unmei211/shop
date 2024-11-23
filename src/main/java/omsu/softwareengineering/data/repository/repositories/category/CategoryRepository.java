package omsu.softwareengineering.data.repository.repositories.category;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class CategoryRepository implements IRepository, IFindByIDMethod<CategoryModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String table = "category";

    public CategoryRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register("categoryRepository", this);
    }

    @Override
    public CategoryModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(table, CategoryModel.class)
                .findByID(id);
    }

    public CategoryModel findByName(final String name) {
        NullValidate.validOrThrow(new FindException("Arguments is null"), name);

        final String sql = "SELECT id, name FROM category WHERE name = ? LIMIT 1";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeQuery();
            return extractor.one(CategoryModel.class, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }

    public void insert(final CategoryModel category) throws InsertException {
        final String sql = "INSERT INTO category (id, name) VALUES (?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, category.getName());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
