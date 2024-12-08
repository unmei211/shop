package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Сервис для работы с продуктами.
 * Обеспечивает создание, получение, обновление и удаление продуктов.
 */
@Slf4j
public class ProductService {

    // Репозитории для работы с продуктами и категориями
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final CategoryRepository categoryRepository = IOC.get("categoryRepository");

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public ProductService() {
        IOC.register(this);
    }

    /**
     * Получает продукт по ID.
     *
     * @param id Идентификатор продукта.
     * @return Модель продукта или null, если продукт не найден.
     */
    public ProductModel getProductByID(final String id) {
        ProductModel productModel = null;
        try {
            productModel = productRepository.findByID(id);  // Поиск продукта по ID
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductByID: " + e.getMessage());
        }
        return productModel;
    }

    /**
     * Получает продукт по имени.
     *
     * @param name Наименование продукта.
     * @return Модель продукта или null, если продукт не найден.
     */
    public ProductModel getProductByName(final String name) {
        ProductModel productModel = null;
        try {
            productModel = productRepository.findByName(name);  // Поиск продукта по имени
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductByName: " + e.getMessage());
        }
        return productModel;
    }

    /**
     * Обновляет или вставляет продукт в систему.
     * Если продукт с таким именем уже существует, он не будет добавлен.
     *
     * @param productModel Модель продукта, которую нужно добавить или обновить.
     */
    public void upsertProduct(final ProductModel productModel) {
        try {
            categoryRepository.findByID(productModel.getCategoryID());  // Проверка на наличие категории
        } catch (FindException e) {
            System.out.println("Can't find category");
        }

        try {
            // Проверка на существование продукта с таким именем
            if (productRepository.findByName(productModel.getName()) != null) {
                log.info("Product with name " + productModel.getName() + " already exists");
                return;
            }
        } catch (FindException e) {
            System.out.println("Can't find product with name " + productModel.getName());
        }
        try {
            // Вставка нового продукта
            productRepository.insert(productModel);
            log.info("Product inserted successfully: productName = {}", productModel.getName());
        } catch (InsertException e) {
            log.info("Product can't insert: productName = {}", productModel.getName());
        }
    }

    /**
     * Удаляет продукт по имени.
     *
     * @param productName Наименование продукта для удаления.
     */
    public void deleteProductByName(final String productName) {
        ProductModel productModel = null;
        try {
            // Поиск продукта по имени
            productModel = productRepository.findByName(productName);
        } catch (FindException e) {
            log.info("Can't find product by name: {}", productName);
            return;
        }

        try {
            // Удаление продукта по ID
            productRepository.deleteByID(productModel.getId());
            log.info("Product deleted successfully: productName = {}", productModel.getName());
        } catch (DeleteException e) {
            log.info("Can't delete product: {}", e.getMessage());
        }
    }
}
