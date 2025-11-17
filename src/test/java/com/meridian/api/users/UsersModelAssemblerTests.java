package com.meridian.api.users;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersModelAssemblerTests {

    @Test
    void authorModel_returnsCorrectly() {

        Users user = new Users(123L, "Assembler", "Test");

        UsersModelAssembler userModelAssembler = new UsersModelAssembler();
        EntityModel<Users> model = userModelAssembler.toModel(user);

        assertEquals(123L, model.getContent().getId());
        assertEquals("Assembler", model.getContent().getFirstName());
        assertEquals("Test", model.getContent().getLastName());
    }
}
