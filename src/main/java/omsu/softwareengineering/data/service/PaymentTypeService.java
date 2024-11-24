package omsu.softwareengineering.data.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.paymenttype.PaymentTypeRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.paymenttype.PaymentTypeEnum;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

@Slf4j
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository = IOC.get(PaymentTypeRepository.class);

    public PaymentTypeService() {
        IOC.register(this);
        Arrays.stream(PaymentTypeEnum.values())
                .map(Enum::toString)
                .forEach((paytype) -> {
                    System.out.println(paytype);
                    try {
                        insertByType(paytype);
                    } catch (InsertException e) {
                        return;
                    }
                });
    }

    public PaymentTypeModel getPaymentTypeByID(final String id) {
        PaymentTypeModel paymentTypeModel = null;
        try {
            paymentTypeModel = paymentTypeRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getPaymentTypeByID: " + e.getMessage());
        }
        return paymentTypeModel;
    }

    public PaymentTypeModel getPaymentTypeByType(final String type) {
        PaymentTypeModel paymentTypeModel = null;
        try {
            paymentTypeModel = paymentTypeRepository.findByType(type);
            log.info("getPaymentTypeByType: {}", paymentTypeModel);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getPaymentTypeByType: " + e.getMessage());
        }
        return paymentTypeModel;
    }

    public void insertByType(final String type) {
        paymentTypeRepository.insert(PaymentTypeModel.builder().type(type).build());
    }
}
