package omsu.softwareengineering.service;

import omsu.softwareengineering.data.repository.repositories.paymenttype.PaymentTypeRepository;
import omsu.softwareengineering.data.repository.repositories.purchases.PurchasesRepository;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.model.purchases.PurchasesModel;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusEnum;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Timestamp;

public class PurchasesService {
    private final PurchasesRepository purchasesRepository = IOC.get(PurchasesRepository.class);
    private final PaymentTypeRepository paymentTypeRepository = IOC.get(PaymentTypeRepository.class);
    private final PurchaseStatusRepository purchaseStatusRepository = IOC.get(PurchaseStatusRepository.class);

    public PurchasesService() {
        IOC.register(this);
    }

    public void buy(String productId, String userId, String paymentType, Long price) {
        PaymentTypeModel paymentTypeModel = paymentTypeRepository.findByType(paymentType);
        PurchaseStatusModel purchaseStatusModel = purchaseStatusRepository.findByStatus(PurchaseStatusEnum.Completed.name());
        purchasesRepository.insert(
                PurchasesModel.builder()
                        .purchaseStatusID(purchaseStatusModel.getId())
                        .date(new Timestamp(System.currentTimeMillis()))
                        .productID(productId)
                        .paymentTypeID(paymentTypeModel.getId())
                        .userID(userId)
                        .price(price)
                        .build()
        );
    }
}
