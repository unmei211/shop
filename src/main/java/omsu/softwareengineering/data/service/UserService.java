package omsu.softwareengineering.data.service;


import lombok.extern.slf4j.Slf4j;
import omsu.softwareengineering.data.repository.FindException;
import omsu.softwareengineering.data.repository.repositories.price.PriceRepository;
import omsu.softwareengineering.data.repository.repositories.product.ProductRepository;
import omsu.softwareengineering.data.repository.repositories.user.UserRepository;
import omsu.softwareengineering.model.price.PriceModel;
import omsu.softwareengineering.model.product.ProductModel;
import omsu.softwareengineering.model.user.UserModel;
import omsu.softwareengineering.util.ioc.IOC;

@Slf4j
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

    public void createUser(final String name, final String email) {
        try {
            userRepository.findByName(name);
            userRepository.findByEmail(email);
            log.info("User already exists by name or email: {} {}", name, email);
        } catch (FindException e) {
            userRepository.insert(UserModel.builder().email(email).name(name).build());
        }
    }
}
