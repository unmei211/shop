package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.ioc.IOC;

public class PurchaseStatusService {
    private final PurchaseStatusRepository purchaseStatusRepository = IOC.get(PurchaseStatusRepository.class);

    public PurchaseStatusService() {
        IOC.register(this);
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
}
