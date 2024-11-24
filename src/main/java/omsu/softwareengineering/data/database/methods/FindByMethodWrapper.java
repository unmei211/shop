package omsu.softwareengineering.data.database.methods;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.repository.FindException;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Slf4j
public class FindByMethodWrapper {
    private final Connection connection;
    private final Extractor extractor;

    public <T, A> T findBy(
            String fieldName,
            A value,
            Class<T> modelClazz,
            String table
    ) throws FindException {
        final String sql = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1", table, fieldName);

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setObject(1, value);
            stmt.executeQuery();
            return extractor.one(modelClazz, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }
}
