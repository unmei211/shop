package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

public class DiscountStrategyService {
    private final DiscountStrategyRepository discountStrategyRepository =
            IOC.get(DiscountStrategyRepository.class);

    public DiscountStrategyService() {
        IOC.register(this);
    }

    public DiscountStrategyModel getProductByID(final String id) {
        DiscountStrategyModel discountStrategyModel = null;
        try {
            discountStrategyModel = discountStrategyRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getDiscountStrategyByID: " + e.getMessage());
        }
        return discountStrategyModel;
    }
}
