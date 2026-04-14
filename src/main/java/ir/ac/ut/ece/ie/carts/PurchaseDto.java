package ir.ac.ut.ece.ie.carts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PurchaseDto {
    private Integer bookCount;
    private Integer totalCost;
    private String date;
}
