package by.khomenko.nsp.webcat.common.entity;

import lombok.Data;

@Data
public class Customer extends Entity {

    private Integer customerId;
    private String login;
    private String password;
    private String contacts;
    private String ip;
    private String location;
    private String status;
    private double discount;


}
