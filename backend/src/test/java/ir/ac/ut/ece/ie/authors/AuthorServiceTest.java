package ir.ac.ut.ece.ie.authors;

import ir.ac.ut.ece.ie.auth.AuthRepository;
import ir.ac.ut.ece.ie.common.AuthorNotFoundException;
import ir.ac.ut.ece.ie.common.NotAdminException;
import ir.ac.ut.ece.ie.common.UserNotFoundException;
import ir.ac.ut.ece.ie.testdata.TestDataFactory;
import ir.ac.ut.ece.ie.users.AdminRepository;
import ir.ac.ut.ece.ie.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @MockitoBean
    private AuthorRepository authorRepository;
    @MockitoBean
    private AdminRepository adminRepository;
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
    void addAuthor_validInput_addsAuthor() {
        var request = AddAuthorRequest.builder()
                .username("username")
                .name("name")
                .penName("pen")
                .born("2020-01-01")
                .nationality("nationality")
                .imageLink("http://sample-link")
                .build();

        var user = TestDataFactory.sampleAdminUser();

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(adminRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(authRepository.isLoggedIn(user)).thenReturn(true);

        authorService.addAuthor(request);

        var captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());
        var author = captor.getValue();

        assertEquals(request.getName(), author.getName());
        assertEquals(request.getPenName(), author.getPenName());
        assertEquals(LocalDate.of(2020, 1, 1), author.getBorn());
        assertNull(author.getDied());
        assertEquals(request.getNationality(), author.getNationality());
        assertEquals(user, author.getAdmin());
        assertEquals(request.getImageLink(), author.getImageLink());
    }

    @Test
    void getAuthor_authorNotFound_throwsException() {
        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthor(1L));
    }

    @Test
    void getAuthor_validInput_returnsAuthor() {
        var author = TestDataFactory.sampleAuthor();
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        var authorDto = authorService.getAuthor(author.getId());

        assertEquals(author.getName(), authorDto.getName());
        assertEquals(author.getPenName(), authorDto.getPenName());
        assertEquals(author.getBorn(), authorDto.getBorn());
        assertEquals(author.getDied(), authorDto.getDied());
        assertEquals(author.getNationality(), authorDto.getNationality());
        assertEquals(author.getImageLink(), authorDto.getImageLink());
    }
}
