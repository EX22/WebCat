package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails extends Entity {

    private Integer orderId;
    private Integer productId;
    private double productPrice;
    private Integer productAmount;
    private double discount;


}
