package ir.ac.ut.ece.ie.purchases;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryDto {
    private String username;
    private List<PurchaseDto> purchaseHistory;
}
