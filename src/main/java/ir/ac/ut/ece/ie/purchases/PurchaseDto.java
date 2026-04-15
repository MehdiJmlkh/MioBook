package ir.ac.ut.ece.ie.purchases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseDto {
    private Integer bookCount;
    private Integer totalCost;
    private String date;
}
