package unit.com.meridian.api.users;

import com.meridian.api.users.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersEntityTests {

    @Test
    void usersEntity_gettersAndSetters() {

        Users user = new Users();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }
}
