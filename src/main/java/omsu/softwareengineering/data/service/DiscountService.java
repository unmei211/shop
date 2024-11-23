package omsu.softwareengineering.data.service;

import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.discount.DiscountRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

public class DiscountService {
    private final DiscountRepository discountRepository = IOC.get(DiscountRepository.class);

    public DiscountService() {
        IOC.register(this);
    }

    public DiscountModel getDiscountByID(final String id) {
        DiscountModel model = null;
        try {
            model = discountRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getDiscountByID: " + e.getMessage());
        }
        return model;
    }
}
