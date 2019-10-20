package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Contacts extends Entity {

    String shippingAddressOne;
    String shippingAddressTwo;
    String shippingAddressThree;
    String phoneNumberOne;
    String phoneNumberTwo;
    String emailTwo;
}
