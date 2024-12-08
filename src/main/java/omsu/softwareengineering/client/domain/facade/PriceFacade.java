package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.service.PriceService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Фасад для работы с ценами продуктов.
 * <p>Обеспечивает упрощённый интерфейс для изменения и получения цен продуктов.</p>
 */
@Slf4j
public class PriceFacade implements IFacade {
    private PriceService priceApi = IOC.get(PriceService.class);
    private ProductService productApi = IOC.get(ProductService.class);

    /**
     * Конструктор фасада.
     * <p>Регистрирует экземпляр фасада в IOC-контейнере.</p>
     */
    public PriceFacade() {
        IOC.register(this);
    }

    /**
     * Изменяет цену продукта по его названию.
     *
     * @param name  название продукта.
     * @param price новая цена продукта.
     * @throws IllegalArgumentException если продукт с указанным названием не найден.
     */
    public void changePriceByProductName(String name, Long price) {
        // Получение модели продукта по названию
        ProductModel productModel = productApi.getProductByName(name);
        if (productModel == null) {
            throw new IllegalArgumentException("Product not found: " + name);
        }

        // Изменение цены для продукта
        priceApi.changePrice(
                PriceModel.builder()
                        .price(price)
                        .productID(productModel.getId())
                        .build()
        );

        log.info("Changed price for product '{}': {}", name, price);
    }

    /**
     * Возвращает текущую цену продукта по его названию.
     *
     * @param name название продукта.
     * @return текущая цена продукта.
     * @throws IllegalArgumentException если продукт с указанным названием не найден.
     */
    public Long getPriceByProductName(String name) {
        Long price = priceApi.getProductPriceByProductName(name);
        if (price == null) {
            throw new IllegalArgumentException("Price not found for product: " + name);
        }

        log.info("Retrieved price for product '{}': {}", name, price);
        return price;
    }
}
