package omsu.softwareengineering.data.extractor.commands;

public interface CommandCreator<T> {
    T create();
}
