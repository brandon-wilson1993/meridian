package com.meridian.api.users;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorModelAssemblerTests {

    @Test
    void authorModel_returnsCorrectly() {

        Users author = new Users(123L, "Assembler", "Test");

        UsersModelAssembler authorModelAssembler = new UsersModelAssembler();
        EntityModel<Users> model = authorModelAssembler.toModel(author);

        assertEquals(123L, model.getContent().getId());
        assertEquals("Assembler", model.getContent().getFirstName());
        assertEquals("Test", model.getContent().getLastName());
    }
}
