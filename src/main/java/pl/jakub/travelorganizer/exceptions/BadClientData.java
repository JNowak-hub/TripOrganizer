package pl.jakub.travelorganizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadClientData extends RuntimeException{

    public BadClientData(String message) {
        super(message);
    }
}
