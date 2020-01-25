package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer extends Entity {

    private Integer customerId;
    private String login;
    private String password;
    private String name;
    //TODO Change field below - private Contacts contacts;
    private String contacts;
    private String phoneNumber;
    private String email;
    private String ip;
    private String location;
    private String status;
    private double discount;
    private Role role;


}
