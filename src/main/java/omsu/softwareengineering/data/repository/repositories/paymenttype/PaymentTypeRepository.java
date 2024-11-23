package omsu.softwareengineering.data.repository.repositories.paymenttype;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentTypeRepository implements IRepository, IFindByIDMethod<PaymentTypeModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "paymenttype";

    public PaymentTypeRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    @Override
    public PaymentTypeModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, PaymentTypeModel.class)
                .findByID(id);
    }

    public PaymentTypeModel findByType(final String type) throws FindException {
        final String sql = "SELECT id, type" +
                " FROM paymenttype WHERE type = ? LIMIT 1";
        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.executeQuery();
            return extractor.one(PaymentTypeModel.class, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }

    public void insert(final PaymentTypeModel model) throws InsertException {
        final String sql = "INSERT INTO paymenttype (type, id) VALUES (?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, model.getType());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
