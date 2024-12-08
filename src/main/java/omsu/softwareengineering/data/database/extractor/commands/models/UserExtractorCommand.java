package omsu.softwareengineering.data.database.extractor.commands.models;

import omsu.softwareengineering.data.database.extractor.commands.IExtractorCommand;
import omsu.softwareengineering.model.user.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация {@link IExtractorCommand}, которая извлекает данные для модели {@link UserModel} из {@link ResultSet}.
 * <p>Этот класс преобразует данные из {@link ResultSet} в объект {@link UserModel}, который представляет собой информацию
 * о пользователе, включая его ID, имя и email.</p>
 */
public class UserExtractorCommand implements IExtractorCommand<UserModel> {
    private ResultSet resultSet;

    /**
     * Выполняет извлечение данных из {@link ResultSet} и создает объект {@link UserModel}.
     *
     * @return {@link UserModel} с данными из {@link ResultSet}.
     */
    @Override
    public UserModel execute() {
        try {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            resultSet.next();
            return new UserModel(id, name, email);
        } catch (SQLException e) {
            return null; // Возвращается null, если произошла ошибка при извлечении данных
        }
    }

    /**
     * Настроить извлечение данных из переданного {@link ResultSet}.
     *
     * @param resultSet {@link ResultSet}, из которого будут извлечены данные для модели {@link UserModel}.
     * @return {@link UserModel} с извлеченными данными.
     */
    @Override
    public UserModel setSetExecute(final ResultSet resultSet) {
        this.resultSet = resultSet;
        return execute();
    }
}
