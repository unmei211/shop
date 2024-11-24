package omsu.softwareengineering.data.service;


import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
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

    public ProductModel getProductByName(final String name) {
        ProductModel ProductModel = null;
        try {
            ProductModel = productRepository.findByName(name);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductByName: " + e.getMessage());
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
            log.info("Product inserted successfully: productName = {}", productModel.getName());
        } catch (InsertException e) {
            log.info("Product can't insert: productName = {}", productModel.getName());
        }
    }

    public void deleteProductByName(final String productName) {
        ProductModel productModel = null;
        try {
            productModel = productRepository.findByName(productName);
        } catch (FindException e) {
            log.info("Can't find product by name: {}", productName);
            return;
        }

        try {
            productRepository.deleteByID(productModel.getId());
            log.info("Product deleted successfully: productName = {}", productModel.getName());
        } catch (DeleteException e) {
            log.info("Can't delete product: {}", e.getMessage());
        }
    }
}
