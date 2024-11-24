package omsu.softwareengineering.data.service;

import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.data.repository.repositories.purchases.PurchasesRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

public class PurchasesService {
    private final PurchasesRepository purchasesRepository = IOC.get(PurchasesRepository.class);

    public PurchasesService() {
        IOC.register(this);
    }
}
