package service;

import service.models.UserServiceModel;
import web.models.UserViewModel;

import java.util.List;

public interface UserService {
    void saveUser(UserServiceModel user);

    UserServiceModel findUser(String username, String password);

    List<UserServiceModel> findAll();

    UserServiceModel findUserByUsername(String username);

    void update(UserServiceModel user);

    void unFriend(String username, String friendName);
}
