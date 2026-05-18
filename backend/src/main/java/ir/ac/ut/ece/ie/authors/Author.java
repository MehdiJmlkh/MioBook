package ir.ac.ut.ece.ie.authors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pen_name")
    private String penName;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "born")
    private LocalDate born;

    @Column(name = "died")
    private LocalDate died;

    @Column(name = "image_link")
    private String imageLink;
}
