package omsu.softwareengineering.data.extractor.commands;

import omsu.softwareengineering.util.generation.IAbstractFactory;

import java.util.Optional;

public class ExtractorCommandsFactory implements IAbstractFactory {
    public ExtractorCommandsFactory() {

    }
    @Override
    public <T> Optional<T> create(Class<T> command) {
        return Optional.empty();
    }
}
