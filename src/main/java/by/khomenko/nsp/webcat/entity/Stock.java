package by.khomenko.nsp.webcat.entity;

import lombok.Data;

@Data
public class Stock extends Entity {

    private Integer productId;
    private Integer amountInStock;


}
