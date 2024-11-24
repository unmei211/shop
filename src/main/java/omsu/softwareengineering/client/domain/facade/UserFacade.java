package omsu.softwareengineering.client.domain.facade;

import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.service.CategoryService;
import omsu.softwareengineering.data.service.ProductService;
import omsu.softwareengineering.data.service.UserService;
import omsu.softwareengineering.model.category.CategoryModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
public class UserFacade implements IFacade {
    private UserService userApi = IOC.get(UserService.class);

    public UserFacade() {
        IOC.register(this);
    }

    public void addUser(String name, String email) {
        userApi.createUser(name, email);
    }
}
