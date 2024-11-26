package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.service.CategoryService;
import omsu.softwareengineering.service.ProductService;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class ProductFacade implements IFacade {
    private CategoryService categoryApi = IOC.get(CategoryService.class);
    private ProductService productApi = IOC.get(ProductService.class);

    public ProductFacade() {
        IOC.register(this);
    }

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

    public void deleteProduct(String productName) {
        productApi.deleteProductByName(productName);
    }
}
