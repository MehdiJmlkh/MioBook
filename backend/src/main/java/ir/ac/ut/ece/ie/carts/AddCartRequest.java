package ir.ac.ut.ece.ie.carts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddCartRequest {
    private String username;
    private String title;
}
