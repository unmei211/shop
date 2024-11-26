package omsu.softwareengineering.service;

import omsu.softwareengineering.data.repository.repositories.purchases.PurchasesRepository;
import omsu.softwareengineering.util.ioc.IOC;

public class PurchasesService {
    private final PurchasesRepository purchasesRepository = IOC.get(PurchasesRepository.class);

    public PurchasesService() {
        IOC.register(this);
    }
}
