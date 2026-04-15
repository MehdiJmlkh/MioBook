package ir.ac.ut.ece.ie.purchases;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PurchaseHistoryDto {
    private String username;
    private List<PurchaseDto> purchaseHistory;
}
