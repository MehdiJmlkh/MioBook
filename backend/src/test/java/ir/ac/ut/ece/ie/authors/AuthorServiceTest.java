package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.auth.NotLoggedInException;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.Role;
import ir.ac.ut.ece.ie.users.User;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @MockitoBean
    private AuthorRepository authorRepository;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private AuthRepository authRepository;
    @Autowired
    private AuthorService authorService;

    @Test
    void addAuthor_duplicateName_throwsException() {
        var request = new AddAuthorRequest();
        request.setName("name");

        when(authorRepository.findByName(request.getName())).thenReturn(Optional.of(new Author()));

        assertThrows(AuthorNameAlreadyExistsException.class, () -> authorService.addAuthor(request));
    }

    @Test
    void addAuthor_userNotFound_throwsException() {
        var request = new AddAuthorRequest();
        request.setUsername("username");

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authorService.addAuthor(request));
    }

    @Test
    void addAuthor_notAdminUser_throwsException() {
        var request = new AddAuthorRequest();

        var user = TestDataFactory.sampleCustomerUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotAdminException.class, () -> authorService.addAuthor(request));
    }

    @Test
    void addAuthor_notLoggedInUser_throwsException() {
        var request = new AddAuthorRequest();

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        assertThrows(NotLoggedInException.class, () -> authorService.addAuthor(request));
    }

    @Test
    void addAuthor_validInput_addsAuthor() {
        var request = AddAuthorRequest.builder()
                .username("username")
                .name("name")
                .penName("pen")
                .born("2020-01-01")
                .nationality("nationality")
                .build();

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(user)).thenReturn(true);

        authorService.addAuthor(request);

        verify(authorRepository).addAuthor(argThat(author ->
                author.getName().equals(request.getName()) &&
                author.getPenName().equals(request.getPenName()) &&
                author.getBorn().equals(LocalDate.of(2020, 1, 1)) &&
                author.getNationality().equals(request.getNationality())
        ));
    }

    @Test
    void getAuthor_authorNotFound_throwsException() {
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor("name"));
    }

    @Test
    void getAuthor_validInput_returnsAuthor() {
        var author = new Author();
        author.setName("name");
        when(authorRepository.findByName(author.getName())).thenReturn(Optional.of(author));
        assertEquals(author, authorService.getAuthor("name"));
    }
}
