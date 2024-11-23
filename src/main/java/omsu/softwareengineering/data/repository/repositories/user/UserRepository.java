package omsu.softwareengineering.data.repository.repositories.user;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.UpdateException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.user.UserModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRepository implements IRepository, IFindByIDMethod<UserModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "users";

    public UserRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    @Override
    public UserModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, UserModel.class)
                .findByID(id);
    }

    public UserModel findByName(final String name) throws FindException {
        final String sql = "SELECT id, name, email" +
                " FROM users WHERE name = ? LIMIT 1";
        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeQuery();
            return extractor.one(UserModel.class, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }

    public void insert(final UserModel userModel) throws InsertException {
        final String sql = "INSERT INTO users (id, name, email)" +
                " VALUES (?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, userModel.getName());
            stmt.setString(3, userModel.getEmail());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
