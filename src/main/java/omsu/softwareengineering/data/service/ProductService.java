package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

public class ProductService {
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final CategoryRepository categoryRepository = IOC.get("categoryRepository");

    public ProductService() {
        IOC.register(this);
    }

    public ProductModel getProductByID(final String id) {
        ProductModel ProductModel = null;
        try {
            ProductModel = productRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductByID: " + e.getMessage());
        }
        return ProductModel;
    }

    public void upsertProduct(final ProductModel productModel) {
        try {
            categoryRepository.findByID(productModel.getCategoryID());
        } catch (FindException e) {
            System.out.println("Can't find category");
        }

        try {
            productRepository.insert(productModel);
        } catch (InsertException e) {
            System.out.println("Can't insert product");
        }
    }
}
