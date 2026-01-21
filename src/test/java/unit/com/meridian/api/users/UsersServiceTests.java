package unit.com.meridian.api.users;

import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.api.users.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {

    private static Users users;
    private static UsersDTO usersDTO;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService = new UsersServiceImpl();

    @BeforeAll
    static void beforeAll() {

        users = new Users();
        users.setId(123L);
        users.setFirstName("Testing");
        users.setLastName("Name");
        users.setPassword("$2a$10$hashedPassword");

        usersDTO = new UsersDTO();
        usersDTO.setId(123L);
        usersDTO.setFirstName("Testing");
        usersDTO.setLastName("Name");
        usersDTO.setPassword("Password123!");
    }

    @Test
    void createUsers_shouldCreate_whenusersDTOIsValid() {

        when(passwordEncoder.encode(any(String.class))).thenReturn("$2a$10$hashedPassword");
        when(modelMapper.map(any(Users.class), eq(UsersDTO.class))).thenReturn(usersDTO);
        when(modelMapper.map(any(UsersDTO.class), eq(Users.class))).thenReturn(users);
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        UsersDTO result = usersService.createUser(modelMapper.map(users, UsersDTO.class));

        assertEquals(123L, result.getId());
        assertEquals("Testing", result.getFirstName());
        assertEquals("Name", result.getLastName());

        verify(passwordEncoder).encode(any(String.class));
        verify(usersRepository).save(users);
    }

    @Test
    void deleteUsersById_shouldDelete_whenIdExists() {

        when(usersRepository.existsById(123L)).thenReturn(true);

        usersService.deleteUserById(123L);

        verify(usersRepository).deleteById(123L);
    }

    @Test
    void deleteUsersById_shouldNotDelete_whenIdDoesNotExists() {

        when(usersRepository.existsById(12L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> usersService.deleteUserById(12L));

        verify(usersRepository, never()).deleteById(123L);
    }

    @Test
    void getAllUsers_shouldReturn() {

        Users users1 = new Users();
        users1.setId(456L);
        users1.setFirstName("Testing");
        users1.setLastName("Another");
        users1.setPassword("$2a$10$hashedPassword1");

        Users users2 = new Users();
        users2.setId(789L);
        users2.setFirstName("Test");
        users2.setLastName("Name");
        users2.setPassword("$2a$10$hashedPassword2");

        List<Users> users = Arrays.asList(users1, users2);

        when(usersRepository.findAll()).thenReturn(users);

        List<UsersDTO> result = usersService.getAllUsers();

        assertArrayEquals(users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class)).toArray(), result.toArray());
        verify(usersRepository).findAll();
    }

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsersExist() {

        when(usersRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsersDTO> result = usersService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(usersRepository).findAll();
    }

    @Test
    void getUsersById_shouldReturn_whenIdExists() {

        when(modelMapper.map(any(Users.class), eq(UsersDTO.class))).thenReturn(usersDTO);
        when(usersRepository.existsById(123L)).thenReturn(true);
        when(usersRepository.findById(123L)).thenReturn(Optional.of(users));

        UsersDTO result = usersService.getUserById(123L);

        assertEquals(123L, result.getId());
        verify(usersRepository).findById(123L);
    }

    @Test
    void getUsersById_shouldNotReturn_whenIdDoesNotExists() {

        when(usersRepository.existsById(12L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> usersService.getUserById(12L));
        verify(usersRepository).existsById(12L);
    }

    @Test
    void updateUsers_shouldUpdate_whenUsersDTOIsValid() {

        Users update = new Users();
        update.setId(123L);
        update.setFirstName("Update");
        update.setLastName("Name");
        update.setPassword("$2a$10$newHashedPassword");

        UsersDTO updatedDTO = new UsersDTO();
        updatedDTO.setId(123L);
        updatedDTO.setFirstName("Update");
        updatedDTO.setLastName("Name");
        updatedDTO.setPassword("NewPassword123!");

        when(passwordEncoder.encode(any(String.class))).thenReturn("$2a$10$newHashedPassword");
        when(modelMapper.map(any(Users.class), eq(UsersDTO.class))).thenReturn(updatedDTO);
        when(usersRepository.findById(123L)).thenReturn(Optional.of(users));
        when(usersRepository.save(any(Users.class))).thenReturn(update);

        UsersDTO result = usersService.updateUser(modelMapper.map(update, UsersDTO.class), 123L);

        assertEquals(123L, result.getId());
        assertEquals("Update", result.getFirstName());
        assertEquals("Name", result.getLastName());

        verify(usersRepository).findById(123L);
        verify(passwordEncoder).encode(any(String.class));
        verify(usersRepository).save(any(Users.class)); // object changes in lamba function in service
    }

    @Test
    void updateUsers_shouldNotUpdate_whenIdIsDoesNotExist() {

        assertThrows(ResourceNotFoundException.class, () -> usersService.updateUser(modelMapper.map(users, UsersDTO.class), 12L));

        verify(usersRepository).findById(12L);
    }
}
