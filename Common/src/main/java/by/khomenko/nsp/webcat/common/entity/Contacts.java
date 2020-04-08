package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Contacts extends Entity {

    private Integer id;
    private Integer customerId;
    private String lastName;
    private String shippingAddress;
    private String country;
    private String state;
    private String zipCode;

}
