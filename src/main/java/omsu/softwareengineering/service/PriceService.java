package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Сервис для работы с ценами продуктов.
 * Обеспечивает операции с ценами продуктов, включая получение, удаление и изменение цен.
 */
@Slf4j
public class PriceService {

    // Репозитории для работы с продуктами и ценами
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final PriceRepository priceRepository = IOC.get(PriceRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public PriceService() {
        IOC.register(this);
    }

    /**
     * Получает продукт по его ID.
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
     * Получает цену продукта по его имени.
     *
     * @param productName Наименование продукта.
     * @return Цена продукта или null, если цена не найдена.
     */
    public Long getProductPriceByProductName(final String productName) {
        try {
            String productID = productRepository.findByName(productName).getId();  // Поиск ID продукта по имени
            Long price = priceRepository.findByProductID(productID).getPrice();  // Получение цены по ID продукта
            log.info("getProductPriceByProductID: price: {} id: {}", price, productID);
            return price;
        } catch (FindException e) {
            log.error("{} getProductPriceByProductID: {}", this.getClass().getName(), e.getMessage());
            return null;
        }
    }

    /**
     * Удаляет цены продуктов по имени продукта.
     *
     * @param name Наименование продукта.
     */
    public void deleteProductPricesByProductName(final String name) {
        try {
            ProductModel model = productRepository.findByName(name);  // Поиск продукта по имени
            String productID = model.getId();  // Получение ID продукта
            productRepository.deleteProductPrices(productID);  // Удаление цен по ID продукта
        } catch (FindException | DeleteException e) {
            System.out.println("Can't delete prices or not found product");
        }
    }

    /**
     * Изменяет цену продукта.
     *
     * @param priceModel Модель цены.
     */
    public void changePrice(final PriceModel priceModel) {
        try {
            ProductModel productModel = productRepository.findByID(priceModel.getProductID());  // Поиск продукта по ID
            deleteProductPricesByProductName(productModel.getName());  // Удаление старых цен
            priceRepository.upsert(priceModel);  // Вставка новой цены
            log.info("Change Price of {} to {}", priceModel.getProductID(), priceModel.getPrice());
        } catch (FindException e) {
            log.error("Error while changing price: {}", e.getMessage());
        }
    }
}
