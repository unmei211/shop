package omsu.softwareengineering.data.repository.repositories.paymenttype;

import lombok.extern.slf4j.Slf4j;
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

/**
 * Репозиторий для работы с типами оплаты в базе данных.
 * <p>Реализует методы для поиска типов оплаты по ID и типу, а также для добавления новых типов оплаты в базу данных.</p>
 * <p>Работает с таблицей "paymenttype" в базе данных.</p>
 */
@Slf4j
public class PaymentTypeRepository implements IRepository, IFindByIDMethod<PaymentTypeModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String targetTable = "paymenttype";

    /**
     * Конструктор класса, инициализирует соединение с базой данных и необходимые сервисы.
     * Регистрирует репозиторий в контейнере IoC.
     */
    public PaymentTypeRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    /**
     * Находит тип оплаты по ID.
     *
     * @param id Идентификатор типа оплаты.
     * @return Модель типа оплаты.
     * @throws FindException Если тип оплаты с таким ID не найден или произошла ошибка при выполнении запроса.
     */
    @Override
    public PaymentTypeModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(targetTable, PaymentTypeModel.class)
                .findByID(id);
    }

    /**
     * Находит тип оплаты по его названию.
     *
     * @param type Название типа оплаты.
     * @return Модель типа оплаты.
     * @throws FindException Если тип оплаты с таким названием не найден или произошла ошибка при выполнении запроса.
     */
    public PaymentTypeModel findByType(String type) throws FindException {
        PaymentTypeModel model = method.findBy("type", type, PaymentTypeModel.class, targetTable);
        log.info("Find PaymentType by type: {}", model.getType());
        return model;
    }

    /**
     * Добавляет новый тип оплаты в базу данных.
     *
     * @param model Модель типа оплаты.
     * @throws InsertException Если произошла ошибка при добавлении типа оплаты.
     */
    public void insert(final PaymentTypeModel model) throws InsertException {
        final String sql = "INSERT INTO paymenttype (id, type) VALUES (?, ?)";

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
