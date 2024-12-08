package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.user.UserRepository;
import omsu.softwareengineering.model.user.UserModel;
import omsu.softwareengineering.util.ioc.IOC;

/**
 * Сервис для работы с пользователями.
 * Обеспечивает создание и поиск пользователей.
 */
@Slf4j
public class UserService {
    // Репозиторий для работы с пользователями
    private final UserRepository userRepository = IOC.get(UserRepository.class);

    /**
     * Конструктор, который регистрирует сервис в контейнере зависимостей.
     */
    public UserService() {
        IOC.register(this);
    }

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Модель пользователя.
     */
    public UserModel getUserByID(final String id) {
        UserModel model = null;
        try {
            model = userRepository.findByID(id);  // Поиск пользователя по ID
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getUserByID: " + e.getMessage());
        }
        return model;
    }

    /**
     * Создание нового пользователя.
     * Если пользователь с таким именем или email уже существует, то он не будет создан.
     *
     * @param name Имя пользователя.
     * @param email Email пользователя.
     */
    public void createUser(final String name, final String email) {
        try {
            userRepository.findByName(name);  // Проверка наличия пользователя по имени
            userRepository.findByEmail(email);  // Проверка наличия пользователя по email
            log.info("User already exists by name or email: {} {}", name, email);
        } catch (FindException e) {
            // Если пользователь не найден, создаем нового
            userRepository.insert(UserModel.builder().email(email).name(name).build());
        }
    }
}
