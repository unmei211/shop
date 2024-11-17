package omsu.softwareengineering.data.extractor.commands;

import omsu.softwareengineering.util.generation.IResultingCommand;

import java.sql.ResultSet;

public interface IExtractorCommand<T> extends IResultingCommand<T> {
    @Override
    T execute();

    T setSetExecute(final ResultSet resultSet);
}
