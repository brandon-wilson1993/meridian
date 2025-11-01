package com.meridian.api.authors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void shouldCreateWhenValid() {

        Author author = new Author(123L, "Testing", "Name");
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.createAuthor(author);

        assertEquals(123L, result.getId());
        assertEquals("Testing", result.getFirstName());
        assertEquals("Name", result.getLastName());
    }

    @Test
    public void shouldReturnWhenIdExists() {

        Author author = new Author(123L, "Testing", "Name");
        when(authorRepository.findById(123L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(123L);

        assertEquals(123L, result.getId());
        verify(authorRepository).findById(123L);
    }
}
