package omsu.softwareengineering.data.service;


import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.user.UserRepository;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.user.UserModel;
import omsu.softwareengineering.util.ioc.IOC;

public class UserService {
    private final UserRepository userRepository = IOC.get(UserRepository.class);

    public UserService() {
        IOC.register(this);
    }

    public UserModel getUserByID(final String id) {
        UserModel model = null;
        try {
            model = userRepository.findByID(id);
        } catch (FindException e) {
            System.out.println(this.getClass().getName() + " getUserByID: " + e.getMessage());
        }
        return model;
    }
}
