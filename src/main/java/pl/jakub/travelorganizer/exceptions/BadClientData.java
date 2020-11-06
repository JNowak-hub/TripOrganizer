package pl.jakub.travelorganizer.exceptions;

public class BadClientData extends RuntimeException{

    public BadClientData(String message) {
        super(message);
    }
}
