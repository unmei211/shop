package omsu.softwareengineering.data.repository.repositories.product_discount;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductDiscountRepository implements IRepository, IFindByIDMethod<ProductDiscountModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "product_discount";

    public ProductDiscountRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    @Override
    public ProductDiscountModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, ProductDiscountModel.class)
                .findByID(id);
    }

    public ProductDiscountModel findByProductID(String productID) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), productID);

        return method
                .findBy("product_id", productID, ProductDiscountModel.class, targetTable);
    }

    public void insert(ProductDiscountModel model) throws InsertException {
        final String sql = "INSERT INTO product_discount " +
                "(id, product_id, discount_id)" +
                " VALUES (?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, model.getProductID());
            stmt.setString(3, model.getDiscountID());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
