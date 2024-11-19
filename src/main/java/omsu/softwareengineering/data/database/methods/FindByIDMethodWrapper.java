package omsu.softwareengineering.data.database.methods;

import lombok.RequiredArgsConstructor;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class FindByIDMethodWrapper<T> implements IFindByIDMethod<T> {
    private final String table;
    private final Connection connection;
    private final Extractor extractor;
    private final Class<T> clazz;

    @Override
    public T findByID(String id) throws FindException {
        final String sql = String.format("SELECT * FROM %s WHERE id = ? LIMIT 1", table);

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeQuery();
            return extractor.one(clazz, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }
}
