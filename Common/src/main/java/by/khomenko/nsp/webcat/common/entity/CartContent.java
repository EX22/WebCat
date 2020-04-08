package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartContent extends Entity {

    private Integer cartId;
    private Integer productId;
    private Integer productCount;
}
