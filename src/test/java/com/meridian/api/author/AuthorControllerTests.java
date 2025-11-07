package com.meridian.api.author;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AuthorControllerTests {

    @Mock
    private AuthorService authorService = new AuthorServiceImpl();

    @InjectMocks
    private AuthorController authorController;

    @Test
    void authorConstructor_modelAssembler_isEqual() {
        
    }
}
