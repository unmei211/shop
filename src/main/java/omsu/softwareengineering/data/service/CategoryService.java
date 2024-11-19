package omsu.softwareengineering.data.service;

import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.InsertException;
import omsu.softwareengineering.data.repository.category.CategoryRepository;
import omsu.softwareengineering.model.category.CategoryEnum;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.util.ioc.IOC;

import java.util.Arrays;

public class CategoryService {
    private final CategoryRepository categoryRepository = IOC.get("categoryRepository");

    public CategoryService() {
        IOC.register(this);
        Arrays.stream(CategoryEnum.values())
                .map(Enum::toString)
                .forEach((cat) -> {
                    System.out.println(cat);
                    try {
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

    public void upsertCategoryByName(final String name) {
        categoryRepository.insert(CategoryModel.builder().name(name).build());
    }
}
