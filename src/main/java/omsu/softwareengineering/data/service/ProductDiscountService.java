package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.product_discount.ProductDiscountRepository;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.util.ioc.IOC;

public class ProductDiscountService {
    private final ProductDiscountRepository productDiscountRepository = IOC.get(ProductDiscountRepository.class);

    public ProductDiscountService() {
        IOC.register(this);
    }

    public ProductDiscountModel getProductDiscountByID(final String id) {
        ProductDiscountModel model = null;
        try {
            model = productDiscountRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getProductDiscountByID: " + e.getMessage());
        }
        return model;
    }
}
