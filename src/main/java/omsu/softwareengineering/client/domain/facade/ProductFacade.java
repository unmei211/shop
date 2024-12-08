package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.service.CategoryService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Фасад для работы с продуктами и категориями.
 * <p>
 * Содержит методы для управления продуктами, включая их добавление и удаление.
 * </p>
 */
@Slf4j
public class ProductFacade implements IFacade {

    private CategoryService categoryApi = IOC.get(CategoryService.class);
    private ProductService productApi = IOC.get(ProductService.class);

    /**
     * Конструктор по умолчанию. Регистрирует данный фасад в IOC.
     */
    public ProductFacade() {
        IOC.register(this);
    }

    /**
     * Добавляет продукт в указанную категорию.
     *
     * @param categoryName название категории, к которой будет привязан продукт.
     * @param productName  название добавляемого продукта.
     * @param amount       количество единиц продукта.
     */
    public void addProduct(String categoryName, String productName, Long amount) {
        CategoryModel categoryModel = categoryApi.getCategoryByName(categoryName);

        productApi.upsertProduct(ProductModel
                .builder()
                .name(productName)
                .amount(amount)
                .categoryID(categoryModel.getId())
                .deleted(false)
                .build()
        );
    }

    /**
     * Удаляет продукт по его названию.
     *
     * @param productName название продукта, который нужно удалить.
     */
    public void deleteProduct(String productName) {
        productApi.deleteProductByName(productName);
    }
}
