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
public class FriendsBean extends BaseBean{

    private List<UserViewModel> friends;
    private UserService userService;
    private ModelMapper mapper;

    @Inject
    public FriendsBean(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init(){
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        friends = userService.findAll().stream()
                .filter(f->f.getFriends().stream()
                .map(UserServiceModel::getUsername)
                .collect(Collectors.toList())
                .contains(username))
                .map(f->mapper.map(f, UserViewModel.class))
                .collect(Collectors.toList());
    }

    public List<UserViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserViewModel> friends) {
        this.friends = friends;
    }

    public void deleteFriend(String friendName) throws IOException {
        String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        userService.unFriend(username, friendName);
        redirect("/friends");
    }
}
