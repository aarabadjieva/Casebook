package web.beans;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import service.UserService;
import service.models.UserServiceModel;
import web.models.UserViewModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Named
@RequestScoped
public class HomeBean extends BaseBean{

    private List<UserViewModel> friends;
    private UserService userService;
    private ModelMapper mapper;

    @Inject
    public HomeBean(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init(){
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        this.friends = userService.findAll().stream()
                .filter(f-> !f.getUsername().equals(username) &&
                        !f.getFriends().stream()
                .map(UserServiceModel::getUsername)
                .collect(Collectors.toList()).contains(username))
                .map(f->mapper.map(f, UserViewModel.class))
                .collect(Collectors.toList());

        getFriends().forEach(f->f.setGender(f.getGender().toLowerCase()));
    }

    public List<UserViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserViewModel> friends) {
        this.friends = friends;
    }

    public void addFriend(String friendName) throws IOException {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        UserServiceModel user = userService.findUserByUsername(username);
        UserServiceModel friend = userService.findUserByUsername(friendName);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        userService.update(user);
        userService.update(friend);
        redirect("/home");
    }
}
