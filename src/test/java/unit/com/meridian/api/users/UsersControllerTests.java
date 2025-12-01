package unit.com.meridian.api.users;

import com.meridian.api.users.UsersController;
import com.meridian.api.users.UsersDTO;
import com.meridian.api.users.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTests {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController = new UsersController();

    @Test
    void authorController_createAuthor() {

        UsersDTO user = new UsersDTO();
        user.setId(null);
        user.setFirstName("Testing");
        user.setLastName("Controller");

        UsersDTO createdUser = new UsersDTO();
        createdUser.setId(123L);
        createdUser.setFirstName("Testing");
        createdUser.setLastName("Controller");

        when(usersService.createUser(user)).thenReturn(createdUser);

        ResponseEntity<UsersDTO> result = usersController.createUser(user);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(123L, result.getBody().getId());
        assertEquals("Testing", result.getBody().getFirstName());
        assertEquals("Controller", result.getBody().getLastName());
    }

    @Test
    void authorController_deleteAuthorById() {

        ResponseEntity<UsersDTO> result = usersController.deleteUserById(123L);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void authorController_getAuthorById() {

        UsersDTO getUser = new UsersDTO();
        getUser.setId(1234L);
        getUser.setFirstName("Testing");
        getUser.setLastName("Controller1");

        when(usersService.getUserById(1234L)).thenReturn(getUser);

        ResponseEntity<UsersDTO> result = usersController.getUserById(1234L);

        assertEquals(1234L, result.getBody().getId());
        assertEquals("Testing", result.getBody().getFirstName());
        assertEquals("Controller1", result.getBody().getLastName());
    }

    @Test
    void authorController_getAllAuthors() {

        UsersDTO getUser1 = new UsersDTO();
        getUser1.setId(1234L);
        getUser1.setFirstName("Testing");
        getUser1.setLastName("Controller1");

        UsersDTO getUser2 = new UsersDTO();
        getUser2.setId(246L);
        getUser2.setFirstName("Testing");
        getUser2.setLastName("Controller2");

        final List<UsersDTO> users = List.of(getUser1, getUser2);

        when(usersService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UsersDTO>> result = usersController.getAllUsers();

        assertEquals(2, result.getBody().size());
        assertEquals(getUser1, result.getBody().get(0));
        assertEquals(getUser2, result.getBody().get(1));
    }

    @Test
    void authorController_updateAuthor() {

        UsersDTO returnedUser = new UsersDTO();
        returnedUser.setId(123L);
        returnedUser.setFirstName("Different");
        returnedUser.setLastName("Name");

        when(usersService.updateUser(returnedUser, 123L)).thenReturn(returnedUser);

        ResponseEntity<UsersDTO> result = usersController.updateUser(returnedUser, 123L);

        assertEquals(123L, result.getBody().getId());
        assertEquals("Different", result.getBody().getFirstName());
        assertEquals("Name", result.getBody().getLastName());
    }
}
