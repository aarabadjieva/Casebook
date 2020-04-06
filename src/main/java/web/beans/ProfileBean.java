package web.beans;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import service.UserService;
import web.models.UserViewModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor
@Named
@RequestScoped
public class ProfileBean {

    private UserViewModel user;
    private UserService userService;
    private ModelMapper mapper;

    @Inject
    public ProfileBean(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostConstruct
    private void init(){
        String username = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext()
                .getRequest()).getParameter("username");
        setUser(mapper.map(userService.findUserByUsername(username), UserViewModel.class));
        System.out.println();
    }

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public String genderToLowerCase(){
        return user.getGender().toLowerCase();
    }
}
