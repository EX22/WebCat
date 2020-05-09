package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartContent extends Entity {

    private Integer customerId;

    /** Key - productId
     * Value - productCount */
    private Map<Integer, Integer> products = new HashMap<>();

    /** Key - productId
     * Value - product */
    private Map<Integer, Product> productInfo = new HashMap<>();


    public void addProduct(Integer productId, Integer productCount){

            products.put(productId, productCount);

    }

    public void addProduct(Integer productId){

        if (products.containsKey(productId)){
            products.put(productId, products.get(productId) + 1);
        } else {
            products.put(productId, 1);
        }

    }

    public void setProductInfo(List<Product> products){
        for (Product product : products){
            productInfo.put(product.getProductId(), product);
        }
    }

    public Integer getProductCount(){

        return products.keySet().size();
    }
}
