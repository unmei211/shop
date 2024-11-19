package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.product.ProductRepository;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

public class ProductService {
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);

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

    public void upsertProductByName(final String name) {
        productRepository.insert(ProductModel.builder().name(name).build());
    }
}
