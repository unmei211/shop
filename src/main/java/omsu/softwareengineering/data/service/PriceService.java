package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

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

    public void changePrice(final PriceModel priceModel) {
        productRepository.findByID(priceModel.getProductID());
        priceRepository.upsert(priceModel);
    }
}
