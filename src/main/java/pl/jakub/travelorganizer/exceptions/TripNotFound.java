package pl.jakub.travelorganizer.exceptions;

public class TripNotFound extends RuntimeException{

    public TripNotFound(String message) {
        super(message);
    }
}
