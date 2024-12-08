package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

/**
 * Сервис для работы с категориями товаров.
 * Обеспечивает взаимодействие с репозиторием категорий и выполнением операций добавления/обновления категорий.
 */
@Slf4j
public class CategoryService {

    // Репозиторий для работы с категориями
    private final CategoryRepository categoryRepository = IOC.get("categoryRepository");

    /**
     * Конструктор, который инициализирует сервис и выполняет загрузку всех категорий по умолчанию.
     * Для каждой категории из перечисления {@link CategoryEnum} будет выполнена попытка вставки в репозиторий.
     */
    public CategoryService() {
        IOC.register(this);
        Arrays.stream(CategoryEnum.values())  // Перебор всех значений перечисления категорий
                .map(Enum::toString)
                .forEach((cat) -> {
                    try {
                        log.info("upsertCategoryByName: {}", cat); // Логируем добавление
                        upsertCategoryByName(cat);  // Добавление или обновление категории
                    } catch (InsertException e) {
                        return; // В случае ошибки вставки продолжаем выполнение
                    }
                });
    }

    /**
     * Получает категорию по ID.
     *
     * @param id Идентификатор категории.
     * @return Модель категории или null, если категория не найдена.
     */
    public CategoryModel getCategoryByID(final String id) {
        CategoryModel categoryModel = null;
        try {
            categoryModel = categoryRepository.findByID(id);  // Поиск категории по ID
        } catch (FindException e) {
            log.error("getCategoryByID: {}", e.getMessage());
        }
        return categoryModel;
    }

    /**
     * Получает категорию по имени.
     *
     * @param name Название категории.
     * @return Модель категории или null, если категория не найдена.
     */
    public CategoryModel getCategoryByName(final String name) {
        CategoryModel categoryModel = null;
        try {
            categoryModel = categoryRepository.findByName(name);  // Поиск категории по имени
            log.info("getCategoryByName: {}", name);  // Логируем поиск
        } catch (FindException e) {
            log.error("getCategoryByName: {}", e.getMessage());
        }
        return categoryModel;
    }

    /**
     * Добавляет или обновляет категорию по имени.
     *
     * @param name Название категории, которую нужно добавить или обновить.
     */
    public void upsertCategoryByName(final String name) {
        categoryRepository.insert(CategoryModel.builder().name(name).build());  // Добавление или обновление категории
    }
}
