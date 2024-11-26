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

@Slf4j
public class ProductDiscountService {
    private final ProductDiscountRepository productDiscountRepository = IOC.get(ProductDiscountRepository.class);
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final DiscountRepository discountRepository = IOC.get(DiscountRepository.class);

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

    public ProductDiscountModel getProductDiscountByProductID(final String productID) {
        return productDiscountRepository.findByProductID(productID);
    }

    public void bundleProductViaDiscount(String productName, String discountStrategyID) {
        ProductModel productModel = productRepository.findByName(productName);
        ProductDiscountModel productDiscountModel = null;
        try {
            productDiscountModel = getProductDiscountByID(productModel.getId());
        } catch (FindException e) {
            log.info("Not found product discount with productID: " + productModel.getId());
        }
        if (productDiscountModel != null) {
            log.info("Discount already accept");
            return;
        }

        DiscountModel discountModel = discountRepository.findByDiscountStrategyID(discountStrategyID);
        try {
            productDiscountRepository.insert(
                    ProductDiscountModel.builder()
                            .discountID(discountModel.getId())
                            .productID(productModel.getId())
                            .build()
            );
        } catch (InsertException e) {
            return;
        }
        log.info("Discount successfully created");
    }
}
