package by.khomenko.nsp.webcat.entity;

import lombok.Data;

@Data
public class User extends Entity {

    private Integer userId;
    private String login;
    private String password;
    private Role role;


}
