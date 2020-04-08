package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart extends Entity {

    private Integer cartId;
    private Integer customerId;

    /** Key - productId
     * Value - productCount */
    private Map<Integer, Integer> products = new HashMap<>();

    /** Key - productId
     * Value - product */
    private Map<Integer, Product> productInfo;

    public void addProduct(Integer productId){

        if (products.containsKey(productId)){
            products.put(productId, products.get(productId) + 1);
        } else {
            products.put(productId, 1);
        }

    }

}
