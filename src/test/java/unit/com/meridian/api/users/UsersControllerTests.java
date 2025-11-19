package unit.com.meridian.api.users;

import com.meridian.api.users.Users;
import com.meridian.api.users.UsersController;
import com.meridian.api.users.UsersModelAssembler;
import com.meridian.api.users.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTests {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController = new UsersController(new UsersModelAssembler());

    @Test
    void authorController_createAuthor() {

        Users author = new Users(null, "Author", "Name");
        Users createdUser = new Users(123L, "Author", "Name");

        when(usersService.createUser(author)).thenReturn(createdUser);

        ResponseEntity<EntityModel<Users>> result = usersController.createUser(author);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Author", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }

    @Test
    void authorController_deleteAuthorById() {

        ResponseEntity<EntityModel<Users>> result = usersController.deleteUserById(123L);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void authorController_getAuthorById() {

        Users user = new Users(123L, "Author", "Name");

        when(usersService.getUserById(123L)).thenReturn(user);

        EntityModel<Users> result = usersController.getUserById(123L);

        assertEquals(123L, result.getContent().getId());
        assertEquals("Author", result.getContent().getFirstName());
        assertEquals("Name", result.getContent().getLastName());
    }

    @Test
    void authorController_getAllAuthors() {

        Users author1 = new Users(123L, "Author", "One");
        Users author2 = new Users(12L, "Author", "Two");

        final List<Users> authors = List.of(author1, author2);

        when(usersService.getAllUsers()).thenReturn(authors);

        CollectionModel<EntityModel<Users>> result = usersController.getAllUsers();

        assertEquals(2, result.getContent().size());
    }

    @Test
    void authorController_updateAuthor() {

        Users updatedUser = new Users(123L, "Updated", "Author");
        Users returnedUser = new Users(123L, "Different", "Name");

        when(usersService.updateUser(updatedUser, 123L)).thenReturn(returnedUser);

        ResponseEntity<EntityModel<Users>> result = usersController.updateUser(updatedUser, 123L);

        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Different", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }
}
