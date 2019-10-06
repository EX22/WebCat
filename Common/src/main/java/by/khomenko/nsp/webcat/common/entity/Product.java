package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product extends Entity {

    private Integer productId;
    private Integer categoryId;
    private String productName;
    private String shortDescription;
    private String fullDescription;
    private double productPrice;
    private double productDiscount;
    private String inStock;
    private String photoPath;
    private String seoAttributes;
    private String outputMarker;


}
