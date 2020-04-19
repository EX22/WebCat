package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order extends Entity {

    public static final String STATUS_NEW = "new";

    private Integer orderId;
    private Integer customerId;
    private Integer cartId;
    private double orderPrice;
    private String orderStatus;
    private String orderDate;
    private String shippingAddress;


}
