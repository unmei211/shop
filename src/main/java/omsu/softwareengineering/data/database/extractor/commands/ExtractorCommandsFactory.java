package omsu.softwareengineering.data.database.extractor.commands;

import omsu.softwareengineering.data.database.extractor.commands.models.*;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.discount.DiscountModel;
import omsu.softwareengineering.model.discountstrategy.DiscountStrategyModel;
import omsu.softwareengineering.model.paymenttype.PaymentTypeModel;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.product_discount.ProductDiscountModel;
import omsu.softwareengineering.model.purchasestatus.PurchaseStatusModel;
import omsu.softwareengineering.model.user.UserModel;
import omsu.softwareengineering.util.generation.IAbstractFactory;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.HashMap;
import java.util.Optional;

/**
 * Фабрика команд извлечения данных, реализующая интерфейс {@link IAbstractFactory}.
 * Этот класс создает команды для извлечения данных из базы данных для различных типов моделей.
 * Каждая команда соответствует определенному типу данных, например, категориям, продуктам, скидкам и т.д.
 * Команды создаются с помощью фабрики {@link CommandCreator} для соответствующих типов данных.
 *
 * <p>Пример использования: фабрика регистрирует команды для различных моделей данных, таких как {@link CategoryModel},
 * {@link ProductModel}, {@link DiscountModel}, и другие.</p>
 *
 * @see CategoryExtractorCommand
 * @see PriceExtractorCommand
 * @see ProductExtractorCommand
 * @see DiscountExtractorCommand
 * @see DiscountStrategyExtractorCommand
 * @see PaymentTypeExtractorCommand
 * @see ProductDiscountExtractorCommand
 * @see UserExtractorCommand
 * @see PurchaseStatusExtractorCommand
 */
public class ExtractorCommandsFactory implements IAbstractFactory {

    private final HashMap<Class<?>, CommandCreator<? extends IExtractorCommand<?>>> commands = new HashMap<>();

    /**
     * Конструктор, который регистрирует команды для различных типов моделей.
     * Каждая модель (например, {@link CategoryModel}, {@link ProductModel}) будет иметь свою соответствующую команду извлечения.
     */
    public ExtractorCommandsFactory() {
        IOC.register("extractorCommandsFactory", this);

        // Регистрируем команды для различных моделей данных
        commands.put(CategoryModel.class, CategoryExtractorCommand::new);
        commands.put(PriceModel.class, PriceExtractorCommand::new);
        commands.put(ProductModel.class, ProductExtractorCommand::new);
        commands.put(DiscountModel.class, DiscountExtractorCommand::new);
        commands.put(DiscountStrategyModel.class, DiscountStrategyExtractorCommand::new);
        commands.put(PaymentTypeModel.class, PaymentTypeExtractorCommand::new);
        commands.put(ProductDiscountModel.class, ProductDiscountExtractorCommand::new);
        commands.put(UserModel.class, UserExtractorCommand::new);
        commands.put(PurchaseStatusModel.class, PurchaseStatusExtractorCommand::new);
    }

    /**
     * Создает команду для извлечения данных для заданного типа модели.
     *
     * @param clazz Класс модели, для которой создается команда.
     * @param <T> Тип модели.
     * @param <D> Тип команды извлечения данных.
     * @return Команда извлечения данных для модели или {@link Optional#empty()} если команда не найдена.
     */
    @Override
    public <T, D> Optional<D> create(Class<T> clazz) {
        D command = (D) commands.get(clazz).create();
        return Optional.of(command);
    }
}
