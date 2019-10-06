package by.khomenko.nsp.webcat.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Order extends Entity {

    private Integer orderId;
    private Integer customerId;
    private double orderPrice;
    private String orderStatus;
    private Date orderDate;
    private String shippingAddress;


}
