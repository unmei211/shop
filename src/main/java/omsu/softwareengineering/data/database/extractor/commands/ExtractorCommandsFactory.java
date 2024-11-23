package omsu.softwareengineering.data.database.extractor.commands;

import omsu.softwareengineering.data.database.extractor.commands.models.CategoryExtractorCommand;
import omsu.softwareengineering.data.database.extractor.commands.models.PriceExtractorCommand;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.price.PriceModel;
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
    }

    @Override
    public <T, D> Optional<D> create(Class<T> clazz) {
        D command = (D) commands.get(clazz).create();
        return Optional.of(command);
    }
}
