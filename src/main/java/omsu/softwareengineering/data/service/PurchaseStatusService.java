package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.model.paymenttype.PaymentTypeEnum;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusEnum;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

public class PurchaseStatusService {
    private final PurchaseStatusRepository purchaseStatusRepository = IOC.get(PurchaseStatusRepository.class);

    public PurchaseStatusService() {
        IOC.register(this);
        Arrays.stream(PurchaseStatusEnum.values())
                .map(Enum::toString)
                .forEach((purstat) -> {
                    System.out.println(purstat);
                    try {
                        insertByType(PurchaseStatusModel.builder().status(purstat).build());
                    } catch (InsertException e) {
                        return;
                    }
                });
    }

    public PurchaseStatusModel getStatusByID(final String id) {
        PurchaseStatusModel model = null;
        try {
            model = purchaseStatusRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getStatusByID: " + e.getMessage());
        }
        return model;
    }

    public void insertByType(PurchaseStatusModel purchaseStatusModel) throws InsertException {
        purchaseStatusRepository.insert(purchaseStatusModel);
    }
}
