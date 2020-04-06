package data.repository;

import data.models.User;
import service.models.UserServiceModel;

import java.util.List;

public interface UserRepository {
    void saveUser(User user);

    User findUser(String username, String password);

    List<User> allUsers();

    User findUserByUsername(String username);

    void update(User user);
}
