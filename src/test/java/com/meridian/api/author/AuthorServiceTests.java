package com.meridian.api.author;

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

    private static Author author;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeAll
    static void beforeAll() {

        author = new Author(123L, "Testing", "Name");
    }

    @Test
    void createAuthor_shouldCreate_whenAuthorIsValid() {

        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.createAuthor(author);

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

        Author author1 = new Author(456L, "Testing", "Another");
        Author author2 = new Author(789L, "Test", "Name");

        List<Author> authors = Arrays.asList(author, author1, author2);

        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();

        assertArrayEquals(authors.toArray(), result.toArray());
        verify(authorRepository).findAll();
    }

    @Test
    void getAuthorById_shouldReturn_whenIdExists() {

        when(authorRepository.findById(123L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(123L);

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

        Author update = new Author(123L, "Update", "Name");

        when(authorRepository.existsById(123L)).thenReturn(true);
        when(authorRepository.findById(123L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(update);

        Author result = authorService.updateAuthor(update, 123L);

        assertEquals(123L, result.getId());
        assertEquals("Update", result.getFirstName());
        assertEquals("Name", result.getLastName());

        verify(authorRepository).save(update);
    }

    @Test
    void updateAuthor_shouldNotUpdate_whenIdIsDoesNotExist() {

        Author update = new Author(12L, "Update", "Name");

        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(update, 12L));

        verify(authorRepository).existsById(12L);
    }
}
