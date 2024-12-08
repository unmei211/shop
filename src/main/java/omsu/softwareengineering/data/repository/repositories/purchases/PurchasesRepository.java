package omsu.softwareengineering.data.repository.repositories.purchases;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.UpdateException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.purchases.PurchasesModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PurchasesRepository implements IRepository, IFindByIDMethod<PurchasesModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "purchases";

    public PurchasesRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    @Override
    public PurchasesModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, PurchasesModel.class)
                .findByID(id);
    }

    public void insert(PurchasesModel purchasesModel) throws InsertException {
        final String sql = "INSERT INTO purchases (id, price,product_id, date, payment_type_id, purchase_status_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?,?,?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setLong(2, purchasesModel.getPrice());
            stmt.setString(3, purchasesModel.getProductID());
            stmt.setTimestamp(4, purchasesModel.getDate());
            stmt.setString(5, purchasesModel.getPaymentTypeID());
            stmt.setString(6, purchasesModel.getPurchaseStatusID());
            stmt.setString(7, purchasesModel.getUserID());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }

    public void returnProduct(PurchasesModel purchasesModel) throws UpdateException {
        final String sql = "UPDATE purchases SET " +
                "purchase_status_id = ? " +
                "WHERE payment_type_id = ? AND user_id = ? AND product_id = ? AND NOT purchase_status_id = ?";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, purchasesModel.getPurchaseStatusID());
            stmt.setString(2, purchasesModel.getPaymentTypeID());
            stmt.setString(3, purchasesModel.getUserID());
            stmt.setString(4, purchasesModel.getProductID());
            stmt.setString(5, purchasesModel.getPurchaseStatusID());
        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
        }
    }
}
