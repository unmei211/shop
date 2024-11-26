package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.discount.DiscountRepository;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Supplier;

@Slf4j
public class DiscountService {
    private final DiscountRepository discountRepository = IOC.get(DiscountRepository.class);
    private final DiscountStrategyRepository discountStrategyRepository = IOC.get(DiscountStrategyRepository.class);

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

    public void initDiscount(final DiscountModel discountModel) {
        if (discountStrategyRepository.findByID(discountModel.getDiscountStrategyID()) == null) {
            log.info("Can't found discount strategy");
            return;
        }

        discountModel.setEnabled(true);
        final Supplier<Timestamp> actualTimestamp = () -> new Timestamp(System.currentTimeMillis());
        discountModel.setEndDate(actualTimestamp.get());
        discountModel.setStartDate(actualTimestamp.get());
        try {
            discountRepository.insert(discountModel);
        } catch (InsertException e) {
            log.info("Can't insert discount");
        }
    }
}
