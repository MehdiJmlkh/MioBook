package ir.ac.ut.ece.ie.carts;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotEnoughCreditException extends RuntimeException {
    private Integer extraCredit;

    public NotEnoughCreditException(Integer extraCredit) {
        this.extraCredit = extraCredit;
    }
}
