package omsu.softwareengineering.data.repository.product;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductRepository {
    private final Connection connection = IOC.get("connection");
    private final Extractor extractor = IOC.get("extractor");
    private final MethodWrapperFactory method = IOC.get(MethodWrapperFactory.class);
    private final String table = "product";

    public ProductRepository() {
        IOC.register(this);
    }

    public ProductModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);
        return method.findByIDMethodWrapper(table, ProductModel.class).findByID(id);
    }

    public void insert(ProductModel product) throws InsertException {
        final String sql = "INSERT INTO product (id, amount, category_id, name) VALUES (?, ?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setLong(2, product.getAmount());
            stmt.setString(3, product.getCategoryID());
            stmt.setString(3, product.getName());

            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
