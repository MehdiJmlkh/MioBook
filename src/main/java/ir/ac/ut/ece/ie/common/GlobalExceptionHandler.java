package ir.ac.ut.ece.ie.common;

import ir.ac.ut.ece.ie.users.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleUnreadableMessage() {
        return ResponseEntity.badRequest().body(
                Response.failed("Invalid request body")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException() {
        return ResponseEntity.badRequest()
                .body(new ErrorDto("User not found."));
    }

    @ExceptionHandler(NotAdminException.class)
    public ResponseEntity<ErrorDto> handleNotAdminException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto("Only admins can add authors."));
    }
}
