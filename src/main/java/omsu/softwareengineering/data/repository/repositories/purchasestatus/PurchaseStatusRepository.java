package omsu.softwareengineering.data.repository.repositories.purchasestatus;

import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.purchases.PurchasesModel;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Репозиторий для управления статусами покупок в базе данных.
 * <p>Предоставляет методы для поиска и добавления записей о статусах покупок.</p>
 * <p>Работает с таблицей "purchasestatus" в базе данных.</p>
 */
public class PurchaseStatusRepository implements IRepository, IFindByIDMethod<PurchaseStatusModel> {
    private final Connection connection = IOC.get("connection");
    private final Extractor extractor = IOC.get("extractor");
    private final MethodWrapperFactory method = IOC.get(MethodWrapperFactory.class);
    private final String table = "purchasestatus";

    /**
     * Конструктор класса, регистрирующий экземпляр в IOC-контейнере.
     */
    public PurchaseStatusRepository() {
        IOC.register(this);
    }

    /**
     * Находит запись о статусе покупки по ее идентификатору.
     *
     * @param id идентификатор статуса покупки.
     * @return объект {@link PurchaseStatusModel}, содержащий данные о статусе.
     * @throws FindException если запись не найдена или произошла ошибка в базе данных.
     */
    @Override
    public PurchaseStatusModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);
        return method.findByIDMethodWrapper(table, PurchaseStatusModel.class).findByID(id);
    }

    /**
     * Находит запись о статусе покупки по значению статуса.
     *
     * @param status строковое значение статуса.
     * @return объект {@link PurchaseStatusModel}, содержащий данные о статусе.
     * @throws FindException если запись не найдена или произошла ошибка в базе данных.
     */
    public PurchaseStatusModel findByStatus(String status) throws FindException {
        return method.findBy("status", status, PurchaseStatusModel.class, table);
    }

    /**
     * Вставляет новый статус покупки в базу данных.
     *
     * @param model объект {@link PurchaseStatusModel}, содержащий данные для вставки.
     * @throws InsertException если произошла ошибка при вставке данных.
     */
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