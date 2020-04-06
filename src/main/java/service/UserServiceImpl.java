package service;

import data.models.User;
import data.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import service.models.UserServiceModel;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public void saveUser(UserServiceModel user) {
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        userRepository.saveUser(mapper.map(user, User.class));
    }

    @Override
    public UserServiceModel findUser(String username, String password) {
        try {
            return mapper.map(userRepository.findUser(username, DigestUtils.sha256Hex(password)), UserServiceModel.class);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<UserServiceModel> findAll() {
        return userRepository.allUsers().stream()
                .map(u->mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return mapper.map(userRepository.findUserByUsername(username), UserServiceModel.class);
    }

    @Override
    public void update(UserServiceModel user) {
        userRepository.update(mapper.map(user, User.class));
    }

    @Override
    public void unFriend(String username, String friendName) {
        User user = userRepository.findUserByUsername(username);
        User friend = userRepository.findUserByUsername(friendName);
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.update(user);
        userRepository.update(friend);
    }
}
