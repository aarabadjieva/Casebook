package web.beans;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import service.UserService;
import service.models.UserServiceModel;
import web.models.UserRegisterModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@NoArgsConstructor
@Named
@RequestScoped
public class RegisterBean extends BaseBean{

    private UserService userService;
    private UserRegisterModel user;
    private ModelMapper mapper;

    @Inject
    public RegisterBean(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init(){
        this.user = new UserRegisterModel();
    }

    public UserRegisterModel getUser() {
        return user;
    }

    public void setUser(UserRegisterModel user) {
        this.user = user;
    }

    public void register() throws IOException {
        if (!user.getPassword().equals(user.getConfirmPassword())){
            return;
        }

        try{
            userService.saveUser(mapper.map(user, UserServiceModel.class));
        }catch (Exception e){
            return;
        }
        redirect("/login");
    }
}
