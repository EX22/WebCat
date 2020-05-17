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
    private String shippingAddress;
    private String country;
    private String state;
    private String zipCode;

    public Contacts (Integer customerId,
                     String customerAddress, String customerCountry,
                     String customerState, String customerZipCode) {

        this.customerId = customerId;
        this.shippingAddress = customerAddress;
        this.country = customerCountry;
        this.state = customerState;
        this.zipCode = customerZipCode;
    }

    @Override
    public String toString() {
        return " shippingAddress='" + shippingAddress + '\''
                + ", country='" + country + '\''
                + ", state='" + state + '\''
                + ", zipCode='" + zipCode + '\'';
    }
}
