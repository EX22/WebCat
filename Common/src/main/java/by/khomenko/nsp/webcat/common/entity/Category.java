package by.khomenko.nsp.webcat.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category extends Entity {

    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private String photoPath;
    private String inStock;
    private String seoAttributes;


}
