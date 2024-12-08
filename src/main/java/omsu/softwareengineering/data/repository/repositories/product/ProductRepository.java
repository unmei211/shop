package omsu.softwareengineering.data.repository.repositories.product;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.database.extractor.Extractor;
import omsu.softwareengineering.data.database.methods.MethodWrapperFactory;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.IRepository;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.generation.IDGen;
import omsu.softwareengineering.util.ioc.IOC;
import omsu.softwareengineering.validation.fields.NullValidate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Репозиторий для работы с продуктами в базе данных.
 * <p>Реализует методы для поиска, добавления, удаления и извлечения информации о продуктах.</p>
 * <p>Работает с таблицей "product" в базе данных.</p>
 */
@Slf4j
public class ProductRepository implements IRepository {
    private final Connection connection = IOC.get("connection");
    private final Extractor extractor = IOC.get("extractor");
    private final MethodWrapperFactory method = IOC.get(MethodWrapperFactory.class);
    private final String table = "product";

    /**
     * Конструктор класса, инициализирует соединение с базой данных и необходимые сервисы.
     * Регистрирует репозиторий в контейнере IoC.
     */
    public ProductRepository() {
        IOC.register(this);
    }

    /**
     * Находит продукт по ID.
     *
     * @param id Идентификатор продукта.
     * @return Модель продукта.
     * @throws FindException Если продукт с таким ID не найден или произошла ошибка при выполнении запроса.
     */
    public ProductModel findByID(String id) throws FindException {
        NullValidate.validOrThrow(new FindException("Arguments is null"), id);
        return method.findByIDMethodWrapper(table, ProductModel.class).findByID(id);
    }

    /**
     * Находит продукт по названию.
     *
     * @param name Название продукта.
     * @return Модель продукта.
     * @throws FindException Если продукт с таким названием не найден.
     */
    public ProductModel findByName(String name) throws FindException {
        ProductModel productModel = method.findByDisableable("name", name, ProductModel.class, table);
        log.info("productModel found by name {}", productModel.getName());
        return productModel;
    }

    /**
     * Удаляет все цены для указанного продукта.
     *
     * @param productID Идентификатор продукта.
     * @throws DeleteException Если не удалось удалить цены.
     */
    public void deleteProductPrices(String productID) throws DeleteException {
        final String sql = "DELETE FROM price WHERE productID = ?";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, productID);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Can't delete productPrices");
        }
    }

    /**
     * Удаляет продукт по ID.
     *
     * @param id Идентификатор продукта.
     * @throws DeleteException Если произошла ошибка при удалении продукта.
     */
    public void deleteByID(String id) throws DeleteException {
        final String sql = "DELETE FROM product WHERE id = ?";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            log.info("Deleted product with id {}", id);
        } catch (SQLException e) {
            log.info("Can't delete product by id {}", id);
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Добавляет новый продукт в базу данных.
     *
     * @param product Модель продукта.
     * @throws InsertException Если произошла ошибка при добавлении данных в базу.
     */
    public void insert(ProductModel product) throws InsertException {
        final String sql = "INSERT INTO product (id, amount, category_id, name, deleted) VALUES (?, ?, ?, ?, ?)";

        try {
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, IDGen.gen());
            stmt.setLong(2, product.getAmount());
            stmt.setString(3, product.getCategoryID());
            stmt.setString(4, product.getName());
            stmt.setBoolean(5, product.getDeleted());
            stmt.execute();
        } catch (SQLException e) {
            throw new InsertException(e.getMessage());
        }
    }
}
