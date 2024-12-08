package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.discount.DiscountRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.product_discount.ProductDiscountRepository;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Сервис для работы с акциями на продукты.
 * Обеспечивает создание, получение и обновление информации о скидках на продукты.
 */
@Slf4j
public class ProductDiscountService {

    // Репозитории для работы с продуктами, скидками и их связями
    private final ProductDiscountRepository productDiscountRepository = IOC.get(ProductDiscountRepository.class);
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final DiscountRepository discountRepository = IOC.get(DiscountRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public ProductDiscountService() {
        IOC.register(this);
    }

    /**
     * Получает скидку на продукт по ID связи.
     *
     * @param id Идентификатор связи скидки с продуктом.
     * @return Модель связи скидки и продукта или null, если связь не найдена.
     */
    public ProductDiscountModel getProductDiscountByID(final String id) {
        ProductDiscountModel model = null;
        try {
            model = productDiscountRepository.findByID(id);  // Поиск скидки по ID связи
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductDiscountByID: " + e.getMessage());
        }
        return model;
    }

    /**
     * Получает скидку на продукт по ID продукта.
     *
     * @param productID Идентификатор продукта.
     * @return Модель связи скидки и продукта или null, если скидка не найдена.
     */
    public ProductDiscountModel getProductDiscountByProductID(final String productID) {
        return productDiscountRepository.findByProductID(productID);  // Поиск скидки по ID продукта
    }

    /**
     * Применяет скидку к продукту по имени продукта и ID стратегии скидки.
     * Если скидка уже применена, она не будет повторно установлена.
     *
     * @param productName Наименование продукта.
     * @param discountStrategyID Идентификатор стратегии скидки.
     */
    public void bundleProductViaDiscount(String productName, String discountStrategyID) {
        ProductModel productModel = productRepository.findByName(productName);  // Поиск продукта по имени
        ProductDiscountModel productDiscountModel = null;
        try {
            productDiscountModel = getProductDiscountByProductID(productModel.getId());  // Проверка на наличие скидки
        } catch (FindException e) {
            log.info("Not found product discount with productID: " + productModel.getId());
        }
        if (productDiscountModel != null) {
            log.info("Discount already applied");
            return;  // Скидка уже применена
        }

        DiscountModel discountModel = discountRepository.findByDiscountStrategyID(discountStrategyID);  // Получение скидки по стратегии
        try {
            // Создание связи между продуктом и скидкой
            productDiscountRepository.insert(
                    ProductDiscountModel.builder()
                            .discountID(discountModel.getId())
                            .productID(productModel.getId())
                            .build()
            );
        } catch (InsertException e) {
            log.error("Failed to apply discount: {}", e.getMessage());
            return;
        }
        log.info("Discount successfully applied to product");
    }
}
