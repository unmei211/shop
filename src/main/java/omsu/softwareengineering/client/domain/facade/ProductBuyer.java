package omsu.softwareengineering.client.domain.facade;

import java.util.function.Consumer;

/**
 * Класс, предоставляющий функционал для покупки продуктов в заданном количестве.
 * <p>
 * Использует интерфейс {@link Consumer} для обработки процесса покупки продукта.
 * </p>
 */
public class ProductBuyer {

    /**
     * Выполняет покупку указанного количества продукта с использованием переданного метода покупки.
     *
     * @param productBuyTransfer объект {@link ProductBuyTransfer}, содержащий данные о покупке:
     *                           название продукта и количество.
     * @param buyMethod          метод, отвечающий за покупку продукта. Принимает название продукта в виде строки.
     * @throws IllegalArgumentException если количество продукта меньше 1.
     */
    public void buyProduct(ProductBuyTransfer productBuyTransfer, Consumer<String> buyMethod) {
        if (productBuyTransfer.getCount() < 1) {
            throw new IllegalArgumentException("Product count must be at least 1");
        }

        // Выполнение покупки указанное количество раз
        for (int i = 0; i < productBuyTransfer.getCount(); i++) {
            buyMethod.accept(productBuyTransfer.getProductName());
        }
    }
}
