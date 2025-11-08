package com.meridian.api.author;

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
public class AuthorControllerTests {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController = new AuthorController(new AuthorModelAssembler());

    @Test
    void authorController_createAuthor() {

        Author author = new Author(null, "Author", "Name");
        Author createdAuthor = new Author(123L, "Author", "Name");

        when(authorService.createAuthor(author)).thenReturn(createdAuthor);

        ResponseEntity<EntityModel<Author>> result = authorController.createAuthor(author);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Author", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }

    @Test
    void authorController_deleteAuthorById() {

        ResponseEntity<EntityModel<Author>> result = authorController.deleteAuthorById(123L);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void authorController_getAuthorById() {

        Author author = new Author(123L, "Author", "Name");

        when(authorService.getAuthorById(123L)).thenReturn(author);

        EntityModel<Author> result = authorController.getAuthorById(123L);

        assertEquals(123L, result.getContent().getId());
        assertEquals("Author", result.getContent().getFirstName());
        assertEquals("Name", result.getContent().getLastName());
    }

    @Test
    void authorController_getAllAuthors() {

        Author author1 = new Author(123L, "Author", "One");
        Author author2 = new Author(12L, "Author", "Two");

        final List<Author> authors = List.of(author1, author2);

        when(authorService.getAllAuthors()).thenReturn(authors);

        CollectionModel<EntityModel<Author>> result = authorController.getAllAuthors();

        assertEquals(2, result.getContent().size());
    }

    @Test
    void authorController_updateAuthor() {

        Author updatedAuthor = new Author(123L, "Updated", "Author");
        Author returnedAuthor = new Author(123L, "Different", "Name");

        when(authorService.updateAuthor(updatedAuthor, 123L)).thenReturn(returnedAuthor);

        ResponseEntity<EntityModel<Author>> result = authorController.updateAuthor(updatedAuthor, 123L);

        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Different", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }
}
