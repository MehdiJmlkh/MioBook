package ir.ac.ut.ece.ie.authors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddAuthorRequest {
    private String username;
    private String name;
    private String penName;
    private String born;
    private String died;
    private String nationality;
}
