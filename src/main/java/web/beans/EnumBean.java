package web.beans;

import data.models.Gender;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EnumBean {

    public Gender[] getGenders(){
        return Gender.values();
    }
}
