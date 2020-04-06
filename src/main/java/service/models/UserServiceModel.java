package service.models;

import data.models.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserServiceModel {

    private String id;
    private String username;
    private String password;
    private Gender gender;
    private List<UserServiceModel> friends;
}
