package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseHistoryDto {
    private String purchaseDate;
    private List<PurchaseItemDto> items;
    private Integer totalCost;
}
