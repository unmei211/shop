package omsu.softwareengineering.data.repository.repositories.discountstrategy;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Репозиторий для работы со стратегиями скидок в базе данных.
 * <p>Реализует методы для поиска стратегий скидок по ID и методу, а также для добавления новых стратегий скидок в базу данных.</p>
 * <p>Работает с таблицей "discountstrategy" в базе данных.</p>
 */
public class DiscountStrategyRepository implements IRepository, IFindByIDMethod<DiscountStrategyModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "discountstrategy";

    /**
     * Конструктор класса, инициализирует соединение с базой данных и необходимые сервисы.
     * Регистрирует репозиторий в контейнере IoC.
     */
    public DiscountStrategyRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    /**
     * Находит стратегию скидки по ID.
     *
     * @param id Идентификатор стратегии скидки.
     * @return Модель стратегии скидки.
     * @throws FindException Если стратегия с таким ID не найдена или произошла ошибка при выполнении запроса.
     */
    @Override
    public DiscountStrategyModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, DiscountStrategyModel.class)
                .findByID(id);
    }

    /**
     * Находит стратегию скидки по методу.
     *
     * @param method Метод стратегии скидки.
     * @return Модель стратегии скидки.
     * @throws FindException Если стратегия с таким методом не найдена или произошла ошибка при выполнении запроса.
     */
    public DiscountStrategyModel findByMethod(final String method) {
        NullValidate.validOrThrow(new FindException("Arguments is null"), method);

        final String sql = "SELECT id, description, method FROM discountstrategy" +
                " WHERE method = ? LIMIT 1";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, method);
            stmt.executeQuery();
            return extractor.one(DiscountStrategyModel.class, stmt);
        } catch (SQLException e) {
            throw new FindException(e.getMessage());
        }
    }

    /**
     * Добавляет новую стратегию скидки в базу данных.
     *
     * @param model Модель стратегии скидки.
     * @throws InsertException Если произошла ошибка при добавлении стратегии скидки.
     */
    public void insert(final DiscountStrategyModel model) throws InsertException {
        final String sql = "INSERT INTO discountstrategy (id, description, method) VALUES (?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setString(2, model.getDescription());
            stmt.setString(3, model.getMethod());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
