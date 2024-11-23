package omsu.softwareengineering.data.repository.repositories.purchasestatus;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class PurchaseStatusRepository implements IRepository, IFindByIDMethod<PurchaseStatusModel> {
    private final Connection connection = IOC.get("connection");
    private final Extractor extractor = IOC.get("extractor");
    private final MethodWrapperFactory method = IOC.get(MethodWrapperFactory.class);
    private final String table = "purchasestatus";

    public PurchaseStatusRepository() {
        IOC.register(this);
    }

    public PurchaseStatusModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);
        return method.findByIDMethodWrapper(table, PurchaseStatusModel.class).findByID(id);
    }

    public void insert(PurchaseStatusModel model) throws InsertException {
        final String sql = "INSERT INTO purchasestatus (id, status) VALUES (?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, model.getStatus());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
