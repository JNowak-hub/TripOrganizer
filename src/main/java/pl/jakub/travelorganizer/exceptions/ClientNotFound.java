package pl.jakub.travelorganizer.exceptions;

public class ClientNotFound extends RuntimeException{

    public ClientNotFound(String message) {
        super(message);
    }
}
