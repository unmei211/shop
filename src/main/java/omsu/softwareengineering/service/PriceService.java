package omsu.softwareengineering.service;


import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.DeleteException;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class PriceService {
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);
    private final PriceRepository priceRepository = IOC.get(PriceRepository.class);

    public PriceService() {
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

    public Long getProductPriceByProductName(final String productName) {
        try {
            String productID = productRepository.findByName(productName).getId();
            Long price = priceRepository.findByProductID(productID).getPrice();
            log.info("getProductPriceByProductID: price: {} id: {}", price, productID);
            return price;
        } catch (FindException e) {
            log.error("{} getProductPriceByProductID: {}", this.getClass().getName(), e.getMessage());
            return null;
        }
    }

    public void deleteProductPricesByProductName(final String name) {
        try {
            ProductModel model = productRepository.findByName(name);
            String productID = model.getId();
            productRepository.deleteProductPrices(productID);
        } catch (FindException | DeleteException e) {
            System.out.println("Can't delete prices or not found product");
        }
    }

    public void changePrice(final PriceModel priceModel) {
        ProductModel productModel = productRepository.findByID(priceModel.getProductID());
        deleteProductPricesByProductName(productModel.getName());
        priceRepository.upsert(priceModel);
        log.info("Change Price of {} to {}", priceModel.getProductID(), priceModel.getPrice());
    }
}
