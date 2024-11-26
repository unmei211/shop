package omsu.softwareengineering.data.repository.repositories.discount;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

public class DiscountRepository implements IRepository, IFindByIDMethod<DiscountModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "discount";

    public DiscountRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    @Override
    public DiscountModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, DiscountModel.class)
                .findByID(id);
    }

    public DiscountModel findByDiscountStrategyID(String discountStrategyID) throws FindException {
        return method
                .findBy("discount_strategy_id", discountStrategyID, DiscountModel.class, targetTable);
    }

    public void insert(DiscountModel model) throws InsertException {
        final String sql = "INSERT INTO discount " +
                "(id, description, start_date, end_date, enabled, discount_strategy_id)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, model.getDescription());
            stmt.setTimestamp(3, model.getStartDate());
            stmt.setTimestamp(4, model.getEndDate());
            stmt.setBoolean(5, model.getEnabled());
            stmt.setString(6, model.getDiscountStrategyID());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
