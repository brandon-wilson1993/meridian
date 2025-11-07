package com.meridian.api.author;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorModelAssemblerTests {

    @Test
    void authorModel_returnsCorrectly() {

        Author author = new Author(123L, "Assembler", "Test");

        AuthorModelAssembler authorModelAssembler = new AuthorModelAssembler();
        EntityModel<Author> model = authorModelAssembler.toModel(author);

        assertEquals(123L, model.getContent().getId());
        assertEquals("Assembler", model.getContent().getFirstName());
        assertEquals("Test", model.getContent().getLastName());
    }
}
