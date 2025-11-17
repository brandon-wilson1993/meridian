package com.meridian.api.users;

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
    private UsersController authorController = new UsersController(new UsersModelAssembler());

    @Test
    void authorController_createAuthor() {

        Users author = new Users(null, "Author", "Name");
        Users createdAuthor = new Users(123L, "Author", "Name");

        when(authorService.createAuthor(author)).thenReturn(createdAuthor);

        ResponseEntity<EntityModel<Users>> result = authorController.createAuthor(author);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Author", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }

    @Test
    void authorController_deleteAuthorById() {

        ResponseEntity<EntityModel<Users>> result = authorController.deleteAuthorById(123L);

        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void authorController_getAuthorById() {

        Users author = new Users(123L, "Author", "Name");

        when(authorService.getAuthorById(123L)).thenReturn(author);

        EntityModel<Users> result = authorController.getAuthorById(123L);

        assertEquals(123L, result.getContent().getId());
        assertEquals("Author", result.getContent().getFirstName());
        assertEquals("Name", result.getContent().getLastName());
    }

    @Test
    void authorController_getAllAuthors() {

        Users author1 = new Users(123L, "Author", "One");
        Users author2 = new Users(12L, "Author", "Two");

        final List<Users> authors = List.of(author1, author2);

        when(authorService.getAllAuthors()).thenReturn(authors);

        CollectionModel<EntityModel<Users>> result = authorController.getAllAuthors();

        assertEquals(2, result.getContent().size());
    }

    @Test
    void authorController_updateAuthor() {

        Users updatedAuthor = new Users(123L, "Updated", "Author");
        Users returnedAuthor = new Users(123L, "Different", "Name");

        when(authorService.updateAuthor(updatedAuthor, 123L)).thenReturn(returnedAuthor);

        ResponseEntity<EntityModel<Users>> result = authorController.updateAuthor(updatedAuthor, 123L);

        assertEquals(123L, result.getBody().getContent().getId());
        assertEquals("Different", result.getBody().getContent().getFirstName());
        assertEquals("Name", result.getBody().getContent().getLastName());
    }
}
