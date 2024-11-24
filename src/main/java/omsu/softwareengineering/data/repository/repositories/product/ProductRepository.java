package omsu.softwareengineering.data.repository.repositories.product;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class ProductRepository implements IRepository {
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

    public ProductModel findByName(String name) throws FindException {
        ProductModel productModel = method.findByDisableable("name", name, ProductModel.class, table);
        log.info("productModel found by name {}", productModel.getName());
        return productModel;
    }

    public void deleteByID(String id) throws DeleteException {
        final String sql = "UPDATE product SET deleted = true WHERE id = ?";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            log.info("Deleted product with id {}", id);
        } catch (SQLException e) {
            log.info("Can't delete product by id {}", id);
            throw new DeleteException(e.getMessage());
        }
    }

    public void insert(ProductModel product) throws InsertException {
        final String sql = "INSERT INTO product (id, amount, category_id, name) VALUES (?, ?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setLong(2, product.getAmount());
            stmt.setString(3, product.getCategoryID());
            stmt.setString(4, product.getName());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
