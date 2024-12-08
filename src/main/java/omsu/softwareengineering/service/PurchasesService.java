package omsu.softwareengineering.service;

import omsu.softwareengineering.data.repository.repositories.paymenttype.PaymentTypeRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.purchases.PurchasesRepository;
import omsu.softwareengineering.data.repository.repositories.purchasestatus.PurchaseStatusRepository;
import omsu.softwareengineering.model.paymenttype.PaymentTypeEnum;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.purchases.PurchasesModel;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusEnum;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.sql.Timestamp;

/**
 * Сервис для работы с покупками.
 * Обеспечивает создание и отмену покупок, а также работу с типами оплаты и статусами покупок.
 */
public class PurchasesService {

    // Репозитории для работы с покупками, типами оплаты, статусами покупок и продуктами
    private final PurchasesRepository purchasesRepository = IOC.get(PurchasesRepository.class);
    private final PaymentTypeRepository paymentTypeRepository = IOC.get(PaymentTypeRepository.class);
    private final PurchaseStatusRepository purchaseStatusRepository = IOC.get(PurchaseStatusRepository.class);
    private final ProductRepository productRepository = IOC.get(ProductRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public PurchasesService() {
        IOC.register(this);
    }

    /**
     * Оформление покупки.
     *
     * @param productId Идентификатор продукта.
     * @param userId Идентификатор пользователя.
     * @param paymentType Тип оплаты.
     * @param price Цена продукта.
     */
    public void buy(String productId, String userId, String paymentType, Long price) {
        PaymentTypeModel paymentTypeModel = paymentTypeRepository.findByType(paymentType);  // Поиск типа оплаты
        PurchaseStatusModel purchaseStatusModel = purchaseStatusRepository.findByStatus(PurchaseStatusEnum.Completed.name());  // Статус покупки - завершен

        // Вставка новой покупки в базу данных
        purchasesRepository.insert(
                PurchasesModel.builder()
                        .purchaseStatusID(purchaseStatusModel.getId())  // Установка статуса
                        .date(new Timestamp(System.currentTimeMillis()))  // Время покупки
                        .productID(productId)  // Продукт
                        .paymentTypeID(paymentTypeModel.getId())  // Тип оплаты
                        .userID(userId)  // Пользователь
                        .price(price)  // Цена
                        .build()
        );
    }

    /**
     * Возврат продукта.
     *
     * @param productName Наименование продукта.
     * @param userId Идентификатор пользователя.
     * @param paymentType Тип оплаты.
     */
    public void returnProduct(String productName, String userId, String paymentType) {
        PaymentTypeModel paymentTypeModel = paymentTypeRepository.findByType(paymentType);  // Поиск типа оплаты
        ProductModel productModel = productRepository.findByName(productName);  // Поиск продукта по имени
        PurchaseStatusModel purchaseStatusModel = purchaseStatusRepository.findByStatus(PurchaseStatusEnum.Canceled.name());  // Статус покупки - отменен

        // Возврат продукта в базу данных
        purchasesRepository.returnProduct(
                PurchasesModel.builder()
                        .paymentTypeID(paymentTypeModel.getId())  // Установка типа оплаты
                        .purchaseStatusID(purchaseStatusModel.getId())  // Статус отмены
                        .productID(productModel.getId())  // Продукт
                        .userID(userId)  // Пользователь
                        .build()
        );
    }
}
