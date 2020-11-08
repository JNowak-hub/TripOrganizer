package pl.jakub.travelorganizer.configure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.jakub.travelorganizer.exceptions.*;
import pl.jakub.travelorganizer.model.Client;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(BadClientData.class)
    public final ResponseEntity<Object> handleBadClientData(BadClientData ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BadGuideData.class)
    public final ResponseEntity<Object> handleBadGuideData(BadGuideData ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BadTripData.class)
    public final ResponseEntity<Object> handleBadTripData(BadTripData ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(GuideNotFound.class)
    public final ResponseEntity<Object> handleGuideNotFound(GuideNotFound ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TripNotFound.class)
    public final ResponseEntity<Object> handleTripNotFound(TripNotFound ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClientNotFound.class)
    public final ResponseEntity<Object> handleClientNotFound(ClientNotFound ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyAssigned.class)
    public final ResponseEntity<Object> handleClientAlreadyAssigned(ClientAlreadyAssigned ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
