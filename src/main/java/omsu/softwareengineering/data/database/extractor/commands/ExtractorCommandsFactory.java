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

public class ExtractorCommandsFactory implements IAbstractFactory {
    private final HashMap<Class<?>, CommandCreator<? extends IExtractorCommand<?>>> commands = new HashMap<>();

    public ExtractorCommandsFactory() {
        IOC.register("extractorCommandsFactory", this);

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

    @Override
    public <T, D> Optional<D> create(Class<T> clazz) {
        D command = (D) commands.get(clazz).create();
        return Optional.of(command);
    }
}
