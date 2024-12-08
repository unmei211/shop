package omsu.softwareengineering.service;

import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusEnum;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

/**
 * Сервис для работы со статусами покупок.
 * Обеспечивает создание и поиск статусов покупок.
 */
public class PurchaseStatusService {

    // Репозиторий для работы со статусами покупок
    private final PurchaseStatusRepository purchaseStatusRepository = IOC.get(PurchaseStatusRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     * Также инициализирует все возможные статусы покупок.
     */
    public PurchaseStatusService() {
        IOC.register(this);

        // Инициализация статусов покупок из перечисления PurchaseStatusEnum
        Arrays.stream(PurchaseStatusEnum.values())
                .map(Enum::toString)
                .forEach((purstat) -> {
                    System.out.println(purstat);
                    try {
                        insertByType(PurchaseStatusModel.builder().status(purstat).build());  // Вставка статуса в базу данных
                    } catch (InsertException e) {
                        return;  // Если не удается вставить, пропускаем
                    }
                });
    }

    /**
     * Поиск статуса покупки по идентификатору.
     *
     * @param id Идентификатор статуса.
     * @return Статус покупки.
     */
    public PurchaseStatusModel getStatusByID(final String id) {
        PurchaseStatusModel model = null;
        try {
            model = purchaseStatusRepository.findByID(id);  // Поиск статуса по ID
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getStatusByID: " + e.getMessage());
        }
        return model;
    }

    /**
     * Вставка нового статуса покупки в базу данных.
     *
     * @param purchaseStatusModel Модель статуса покупки.
     * @throws InsertException Исключение при вставке данных.
     */
    public void insertByType(PurchaseStatusModel purchaseStatusModel) throws InsertException {
        purchaseStatusRepository.insert(purchaseStatusModel);  // Вставка статуса
    }
}
