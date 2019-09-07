package by.khomenko.nsp.webcat.entity;

import lombok.Data;

@Data
public class Product extends Entity {

    private Integer productId;
    private Integer categoryId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double productDiscount;
    private String inStock;
    private String photoPath;
    private String seoAttributes;

}
