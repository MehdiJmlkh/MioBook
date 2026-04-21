package ir.ac.ut.ece.ie.carts;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private String username;
    private Integer totalCost;
    private List<CartItemDto> items;
}
