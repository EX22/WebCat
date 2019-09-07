package by.khomenko.nsp.webcat.entity;

import lombok.Data;

@Data
public class Category extends Entity {

    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private String photoPath;
    private boolean inStock;
    private String seoAttributes;


}
