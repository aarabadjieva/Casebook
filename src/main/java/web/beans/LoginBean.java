package web.beans;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import service.UserService;
import service.models.UserServiceModel;
import web.models.UserLoginModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Map;

@NoArgsConstructor
@Named
@RequestScoped
public class LoginBean extends BaseBean{

    private UserService userService;
    private UserLoginModel model;
    private ModelMapper mapper;

    @Inject
    public LoginBean(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init(){
        this.model = new UserLoginModel();
        System.out.println();
    }

    public UserLoginModel getModel() {
        return model;
    }

    public void setModel(UserLoginModel model) {
        this.model = model;
    }

    public void loginUser() throws IOException {
        UserServiceModel user = userService.findUser(model.getUsername(), model.getPassword());
        if (user==null){
            redirect("/login");
        }else {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("username", user.getUsername());
            redirect("/home");
        }
    }
}
