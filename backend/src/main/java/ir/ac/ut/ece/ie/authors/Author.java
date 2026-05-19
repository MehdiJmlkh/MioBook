package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.books.Book;
import ir.ac.ut.ece.ie.users.Admin;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
