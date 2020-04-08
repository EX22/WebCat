package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer extends Entity {

    private Integer customerId;
    private String login;
    private String password;
    private String name;
    private List<Contacts> contactsList;
    private String phoneNumber;
    private String ip;
    private String location;
    private String status;
    private double discount;
    private Role role;


}
