package omsu.softwareengineering.service.discountCacl;

import omsu.softwareengineering.model.discountstrategy.DiscountStrategyEnum;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.HashMap;
import java.util.function.Function;

/**
 * Класс для вычисления скидки на основе выбранной стратегии.
 * Содержит методы для добавления стратегий и расчета скидки.
 */
public class DiscountCalculator {
    // Словарь стратегий с функциями для расчета скидки
    private final HashMap<String, Function<Long, Long>> discountMap = new HashMap<>();

    /**
     * Конструктор, который регистрирует сервис и определяет стратегии скидок.
     */
    public DiscountCalculator() {
        // Стратегия по умолчанию (без скидки)
        discountMap.put(null, (price) -> price);

        // Стратегия случайного диапазона (скидка 20%)
        discountMap.put(DiscountStrategyEnum.RandomRange.name(), (price) -> (long) (price * 0.8f));

        // Процентная стратегия (скидка 10%)
        discountMap.put(DiscountStrategyEnum.Percentage.name(), (price) -> (long) (price * 0.9f));

        // Количественная стратегия (скидка 40%)
        discountMap.put(DiscountStrategyEnum.Quantitative.name(), (price) -> (long) (price * 0.6f));

        // Регистрируем объект в контейнере зависимостей
        IOC.register(this);
    }

    /**
     * Рассчитывает скидку для указанного метода и цены.
     *
     * @param method Метод скидки (например, "RandomRange", "Percentage", "Quantitative").
     * @param price Исходная цена товара.
     * @return Итоговая цена после применения скидки.
     */
    public Long calc(String method, Long price) {
        // Применяем соответствующую функцию скидки для выбранного метода
        return discountMap.get(method).apply(price);
    }
}
