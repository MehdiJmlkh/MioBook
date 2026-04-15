package ir.ac.ut.ece.ie.purchases;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseListDto {
    private String username;
    private List<PurchaseHistoryDto> purchaseHistory;
}
