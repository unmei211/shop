package omsu.softwareengineering.service;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.repositories.category.CategoryRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository = IOC.get("categoryRepository");

    public CategoryService() {
        IOC.register(this);
        Arrays.stream(CategoryEnum.values())
                .map(Enum::toString)
                .forEach((cat) -> {
                    try {
                        log.info("upsertCategoryByName: {}", cat);
                        upsertCategoryByName(cat);
                    } catch (InsertException e) {
                        return;
                    }
                });
    }

    public CategoryModel getCategoryByID(final String id) {
        CategoryModel categoryModel = null;
        try {
            categoryModel = categoryRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getCategoryByID: " + e.getMessage());
        }
        return categoryModel;
    }

    public CategoryModel getCategoryByName(final String name) {
        CategoryModel categoryModel = null;
        try {
            categoryModel = categoryRepository.findByName(name);
            log.info("getCategoryByName: {}", name);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getCategoryByName: " + e.getMessage());
        }
        return categoryModel;
    }

    public void upsertCategoryByName(final String name) {
        categoryRepository.insert(CategoryModel.builder().name(name).build());
    }
}
