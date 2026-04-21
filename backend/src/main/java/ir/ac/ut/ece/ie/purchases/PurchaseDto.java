package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDto {
    private String purchaseDate;
    private Integer totalCost;
    private List<PurchaseItemDto> items;
}
