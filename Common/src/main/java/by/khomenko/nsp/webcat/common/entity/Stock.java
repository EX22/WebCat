package by.khomenko.nsp.webcat.common.entity;

import lombok.Data;

@Data
public class Stock extends Entity {

    private Integer productId;
    private Integer amountInStock;


}
