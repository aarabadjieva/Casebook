package web.models;

import data.models.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterModel {

    private String username;
    private String password;
    private String confirmPassword;
    private Gender gender;
}
