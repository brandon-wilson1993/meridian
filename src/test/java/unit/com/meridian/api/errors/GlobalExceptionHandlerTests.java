package unit.com.meridian.api.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.meridian.api.errors.ErrorResponse;
import com.meridian.api.errors.GlobalExceptionHandler;
import com.meridian.api.users.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class GlobalExceptionHandlerTests {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void globalExceptionHandler_handleResourceNotFoundException() {

        ResourceNotFoundException resourceNotFoundException =
                new ResourceNotFoundException("Testing message");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleResourceNotFoundException(resourceNotFoundException);

        assertEquals("Not Found", responseEntity.getBody().getError());
        assertEquals("Testing message", responseEntity.getBody().getMessage());
        assertEquals(404, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleGlobalException() {

        Exception exception = new Exception("Testing Message");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleGlobalException(exception);

        assertEquals("Internal Server Error", responseEntity.getBody().getError());
        assertEquals("Testing Message", responseEntity.getBody().getMessage());
        assertEquals(500, responseEntity.getBody().getStatus());
    }
}
