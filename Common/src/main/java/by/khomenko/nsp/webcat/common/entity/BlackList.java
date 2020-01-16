package by.khomenko.nsp.webcat.common.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlackList extends Entity {

    private Integer customerId;
    private String login;
}
