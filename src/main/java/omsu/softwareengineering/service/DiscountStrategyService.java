package omsu.softwareengineering.service;


import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.discountstrategy.DiscountStrategyRepository;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyEnum;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

@Slf4j
public class DiscountStrategyService {
    private final DiscountStrategyRepository discountStrategyRepository =
            IOC.get(DiscountStrategyRepository.class);

    public DiscountStrategyService() {
        IOC.register(this);
        try {
            Arrays.stream(DiscountStrategyEnum.values()).forEach(discountStrategyEnum -> {
                String methodName = discountStrategyEnum.name();
                insertStrategy(DiscountStrategyModel.builder().method(methodName).description(methodName).build());
            });
        } catch (InsertException e) {
            log.info("Cannot insert strategy");
        }
    }

    public DiscountStrategyModel getStrategyByName(String methodName) {
        try {
            return discountStrategyRepository.findByMethod(methodName);
        } catch (FindException e) {
            log.info("Cannot find strategy by method name");
            return null;
        }
    }

    public DiscountStrategyModel getStrategyById(String id) {
        try {
            return discountStrategyRepository.findByID(id);
        } catch (FindException e) {
            log.info("Cannot find strategy by id");
            return null;
        }
    }

    public void insertStrategy(DiscountStrategyModel discountStrategyModel) {
        try {
            discountStrategyRepository.insert(discountStrategyModel);
        } catch (FindException e) {
            log.error("insert strategy error {}", e.getMessage());
        }
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

    public DiscountStrategyModel getDiscountStrategyModelByMethod(final String method) {
        try {
            DiscountStrategyModel model = discountStrategyRepository.findByMethod(method);
            log.info("getDiscountStrategyModelByMethod: {}", method);
            return model;
        } catch (FindException e) {
            log.info("Can't getDiscountStrategyModelByMethod: {}", e.getMessage());
            return null;
        }
    }
}
