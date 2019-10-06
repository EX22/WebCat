package by.khomenko.nsp.webcat.common.entity;

import lombok.Data;

@Data
public class Administration extends Entity {

    private Integer userId;
    private String login;
    private String password;
    private Role role;


}
