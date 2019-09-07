package by.khomenko.nsp.webcat.entity;

import lombok.Data;

@Data
public class OrderDetails extends Entity {

    private Integer orderId;
    private Integer productId;
    private double productPrice;
    private Integer productAmount;
    private double discount;


}
