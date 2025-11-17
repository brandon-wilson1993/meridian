package com.meridian.api.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {

    private static Users author;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks private AuthorService authorService = new AuthorServiceImpl();

    @BeforeAll
    static void beforeAll() {

        author = new Users(123L, "Testing", "Name");
    }

    @Test
    void createAuthor_shouldCreate_whenAuthorIsValid() {

        when(authorRepository.save(author)).thenReturn(author);

        Users result = authorService.createAuthor(author);

        assertEquals(123L, result.getId());
        assertEquals("Testing", result.getFirstName());
        assertEquals("Name", result.getLastName());

        verify(authorRepository).save(author);
    }

    @Test
    void deleteAuthorById_shouldDelete_whenIdExists() {

        when(authorRepository.existsById(123L)).thenReturn(true);

        authorService.deleteAuthorById(123L);

        verify(authorRepository).deleteById(123L);
    }

    @Test
    void deleteAuthorById_shouldNotDelete_whenIdDoesNotExists() {

        when(authorRepository.existsById(12L)).thenReturn(false);

        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteAuthorById(12L));

        verify(authorRepository, never()).deleteById(123L);
    }

    @Test
    void getAllAuthors_shouldReturn() {

        Users author1 = new Users(456L, "Testing", "Another");
        Users author2 = new Users(789L, "Test", "Name");

        List<Users> authors = Arrays.asList(author, author1, author2);

        when(authorRepository.findAll()).thenReturn(authors);

        List<Users> result = authorService.getAllAuthors();

        assertArrayEquals(authors.toArray(), result.toArray());
        verify(authorRepository).findAll();
    }

    @Test
    void getAuthorById_shouldReturn_whenIdExists() {

        when(authorRepository.findById(123L)).thenReturn(Optional.of(author));

        Users result = authorService.getAuthorById(123L);

        assertEquals(123L, result.getId());
        verify(authorRepository).findById(123L);
    }

    @Test
    void getAuthorById_shouldNotReturn_whenIdDoesNotExists() {

        when(authorRepository.findById(12L)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> authorService.getAuthorById(12L));
        verify(authorRepository).findById(12L);
    }

    @Test
    void updateAuthor_shouldUpdate_whenAuthorIsValid() {

        Users update = new Users(123L, "Update", "Name");

        when(authorRepository.findById(123L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Users.class))).thenReturn(update);

        Users result = authorService.updateAuthor(update, 123L);

        assertEquals(123L, result.getId());
        assertEquals("Update", result.getFirstName());
        assertEquals("Name", result.getLastName());

        verify(authorRepository).save(update);
    }

    @Test
    void updateAuthor_shouldNotUpdate_whenIdIsDoesNotExist() {

        Users update = new Users(12L, "Update", "Name");

        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(update, 12L));

        verify(authorRepository).findById(12L);
    }
}
