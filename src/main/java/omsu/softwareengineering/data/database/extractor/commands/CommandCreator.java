package omsu.softwareengineering.data.database.extractor.commands;

public interface CommandCreator<T> {
    T create();
}
