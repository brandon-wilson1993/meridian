//package unit.com.meridian.api.users;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.meridian.api.errors.ResourceNotFoundException;
//import com.meridian.api.users.*;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class UsersServiceTests {
//
//    private static Users author;
//
//    @Mock
//    private UsersRepository usersRepository;
//
//    @InjectMocks private UsersService usersService = new UsersServiceImpl();
//
//    @BeforeAll
//    static void beforeAll() {
//
//        author = new Users(123L, "Testing", "Name");
//    }
//
//    @Test
//    void createAuthor_shouldCreate_whenAuthorIsValid() {
//
//        when(usersRepository.save(author)).thenReturn(author);
//
//        Users result = usersService.createUser(author);
//
//        assertEquals(123L, result.getId());
//        assertEquals("Testing", result.getFirstName());
//        assertEquals("Name", result.getLastName());
//
//        verify(usersRepository).save(author);
//    }
//
//    @Test
//    void deleteAuthorById_shouldDelete_whenIdExists() {
//
//        when(usersRepository.existsById(123L)).thenReturn(true);
//
//        usersService.deleteUserById(123L);
//
//        verify(usersRepository).deleteById(123L);
//    }
//
//    @Test
//    void deleteAuthorById_shouldNotDelete_whenIdDoesNotExists() {
//
//        when(usersRepository.existsById(12L)).thenReturn(false);
//
//        assertThrows(ResourceNotFoundException.class, () -> usersService.deleteUserById(12L));
//
//        verify(usersRepository, never()).deleteById(123L);
//    }
//
//    @Test
//    void getAllAuthors_shouldReturn() {
//
//        Users author1 = new Users(456L, "Testing", "Another");
//        Users author2 = new Users(789L, "Test", "Name");
//
//        List<Users> authors = Arrays.asList(author, author1, author2);
//
//        when(usersRepository.findAll()).thenReturn(authors);
//
//        List<Users> result = usersService.getAllUsers();
//
//        assertArrayEquals(authors.toArray(), result.toArray());
//        verify(usersRepository).findAll();
//    }
//
//    @Test
//    void getAuthorById_shouldReturn_whenIdExists() {
//
//        when(usersRepository.findById(123L)).thenReturn(Optional.of(author));
//
//        Users result = usersService.getUserById(123L);
//
//        assertEquals(123L, result.getId());
//        verify(usersRepository).findById(123L);
//    }
//
//    @Test
//    void getAuthorById_shouldNotReturn_whenIdDoesNotExists() {
//
//        when(usersRepository.findById(12L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> usersService.getUserById(12L));
//        verify(usersRepository).findById(12L);
//    }
//
//    @Test
//    void updateAuthor_shouldUpdate_whenAuthorIsValid() {
//
//        Users update = new Users(123L, "Update", "Name");
//
//        when(usersRepository.findById(123L)).thenReturn(Optional.of(author));
//        when(usersRepository.save(any(Users.class))).thenReturn(update);
//
//        Users result = usersService.updateUser(update, 123L);
//
//        assertEquals(123L, result.getId());
//        assertEquals("Update", result.getFirstName());
//        assertEquals("Name", result.getLastName());
//
//        verify(usersRepository).save(update);
//    }
//
//    @Test
//    void updateAuthor_shouldNotUpdate_whenIdIsDoesNotExist() {
//
//        Users update = new Users(12L, "Update", "Name");
//
//        assertThrows(ResourceNotFoundException.class, () -> usersService.updateUser(update, 12L));
//
//        verify(usersRepository).findById(12L);
//    }
//}
