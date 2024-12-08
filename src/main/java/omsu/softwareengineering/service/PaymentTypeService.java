package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.paymenttype.PaymentTypeRepository;
import omsu.softwareengineering.model.paymenttype.PaymentTypeEnum;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

/**
 * Сервис для работы с типами платежей.
 * Обеспечивает операции с типами платежей, включая их вставку и поиск.
 */
@Slf4j
public class PaymentTypeService {

    // Репозиторий для работы с типами платежей
    private final PaymentTypeRepository paymentTypeRepository = IOC.get(PaymentTypeRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей и инициализирует типы платежей.
     */
    public PaymentTypeService() {
        IOC.register(this);
        Arrays.stream(PaymentTypeEnum.values())
                .map(Enum::toString)
                .forEach((paytype) -> {
                    System.out.println(paytype);
                    try {
                        // Вставка типов платежей в базу
                        insertByType(paytype);
                    } catch (InsertException e) {
                        return;
                    }
                });
    }

    /**
     * Получает тип платежа по его ID.
     *
     * @param id Идентификатор типа платежа.
     * @return Модель типа платежа или null, если тип платежа не найден.
     */
    public PaymentTypeModel getPaymentTypeByID(final String id) {
        PaymentTypeModel paymentTypeModel = null;
        try {
            paymentTypeModel = paymentTypeRepository.findByID(id);  // Поиск типа платежа по ID
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getPaymentTypeByID: " + e.getMessage());
        }
        return paymentTypeModel;
    }

    /**
     * Получает тип платежа по его наименованию.
     *
     * @param type Тип платежа.
     * @return Модель типа платежа или null, если тип платежа не найден.
     */
    public PaymentTypeModel getPaymentTypeByType(final String type) {
        PaymentTypeModel paymentTypeModel = null;
        try {
            paymentTypeModel = paymentTypeRepository.findByType(type);  // Поиск типа платежа по наименованию
            log.info("getPaymentTypeByType: {}", paymentTypeModel);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getPaymentTypeByType: " + e.getMessage());
        }
        return paymentTypeModel;
    }

    /**
     * Вставляет новый тип платежа в репозиторий.
     *
     * @param type Тип платежа.
     */
    public void insertByType(final String type) {
        paymentTypeRepository.insert(PaymentTypeModel.builder().type(type).build());  // Вставка типа платежа
    }
}
