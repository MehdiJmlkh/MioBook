package ir.ac.ut.ece.ie.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    private boolean success;
    private  String message;
}
