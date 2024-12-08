package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.service.UserService;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Фасад для управления пользователями в системе.
 * <p>
 * Содержит методы для создания новых пользователей.
 * </p>
 */
@Slf4j
public class UserFacade implements IFacade {

    private UserService userApi = IOC.get(UserService.class);

    /**
     * Конструктор по умолчанию. Регистрирует данный фасад в IOC.
     */
    public UserFacade() {
        IOC.register(this);
    }

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param name  имя пользователя.
     * @param email email пользователя.
     */
    public void addUser(String name, String email) {
        userApi.createUser(name, email);
    }
}
