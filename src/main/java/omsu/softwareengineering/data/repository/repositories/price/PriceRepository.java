package omsu.softwareengineering.data.repository.repositories.price;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.UpdateException;
import omsu.softwareengineering.data.repository.methods.IFindByIDMethod;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Репозиторий для работы с ценами в базе данных.
 * <p>Реализует методы для поиска, добавления, обновления и вставки/обновления данных о ценах.</p>
 * <p>Работает с таблицей "price" в базе данных.</p>
 */
@Slf4j
public class PriceRepository implements IRepository, IFindByIDMethod<PriceModel> {
    private final Connection connection;
    private final Extractor extractor;
    private final MethodWrapperFactory method;
    private final String table = "price";

    /**
     * Конструктор класса, инициализирует соединение с базой данных и необходимые сервисы.
     * Регистрирует репозиторий в контейнере IoC.
     */
    public PriceRepository() {
        this.connection = IOC.get("connection");
        this.extractor = IOC.get("extractor");
        this.method = IOC.get(MethodWrapperFactory.class);
        IOC.register(this);
    }

    /**
     * Находит цену по ID.
     *
     * @param id Идентификатор цены.
     * @return Модель цены.
     * @throws FindException Если цена с таким ID не найдена или произошла ошибка при выполнении запроса.
     */
    @Override
    public PriceModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);

        return method
                .findByIDMethodWrapper(table, PriceModel.class)
                .findByID(id);
    }

    /**
     * Находит цену по ID продукта.
     *
     * @param productID Идентификатор продукта.
     * @return Модель цены для указанного продукта.
     * @throws FindException Если цена для указанного продукта не найдена.
     */
    public PriceModel findByProductID(final String productID) throws FindException {
        PriceModel priceModel = method.findBy("product_id", productID, PriceModel.class, table);
        log.info("priceModel found by product id {}", productID);
        return priceModel;
    }

    /**
     * Добавляет новую цену в базу данных.
     * Устанавливает текущие дату и время как дату начала и окончания.
     *
     * @param priceModel Модель цены.
     * @throws InsertException Если произошла ошибка при добавлении данных в базу.
     */
    public void insert(final PriceModel priceModel) throws InsertException {
        final String sql = "INSERT INTO price (id, price, start_date, end_date, product_id)" +
                " VALUES (?, ?, ?, ?, ?)";
        final LocalDateTime currentTime = LocalDateTime.now();
        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setLong(2, priceModel.getPrice());
            stmt.setTimestamp(3, Timestamp.valueOf(currentTime));

            // Добавить дату окончания
            // Возможно вынести логику в сервис
            stmt.setTimestamp(4, Timestamp.valueOf(currentTime));
            stmt.setString(5, priceModel.getProductID());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }

    /**
     * Обновляет данные о цене в базе данных.
     * Устанавливает текущие дату и время как дату начала и окончания.
     *
     * @param priceModel Модель цены.
     * @throws UpdateException Если произошла ошибка при обновлении данных в базе.
     */
    public void update(final PriceModel priceModel) throws UpdateException {
        final String sql = "UPDATE price SET id = ?, price = ?, start_date = ?, end_date = ?, product_id = ?" +
                " WHERE id = ?";
        final LocalDateTime currentTime = LocalDateTime.now();
        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, priceModel.getId());
            stmt.setLong(2, priceModel.getPrice());
            stmt.setTimestamp(3, Timestamp.valueOf(currentTime));
            // Добавить дату окончания
            // Возможно вынести логику в сервис
            stmt.setTimestamp(4, Timestamp.valueOf(currentTime));
            stmt.setString(5, priceModel.getProductID());
            stmt.setString(6, priceModel.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Вставляет или обновляет данные о цене в зависимости от существования цены для продукта.
     *
     * @param model Модель цены.
     * @throws UpdateException Если произошла ошибка при вставке или обновлении данных.
     */
    public void upsert(final PriceModel model) throws UpdateException {
        Boolean priceForProductExists = true;
        PriceModel foundedPriceModel = null;
        try {
            foundedPriceModel = findByProductID(model.getProductID());
        } catch (FindException e) {
            priceForProductExists = false;
        }

        if (model.getId() == null && !priceForProductExists) {
            log.info("Price model id is null");
            insert(model);
            log.info("Price model inserted by product id {}", model.getProductID());
        } else {
            if (model.getId() == null) {
                model.setId(foundedPriceModel.getId());
            }
            update(model);
            log.info("Price model updated by product id {}", model.getProductID());
        }
    }
}
