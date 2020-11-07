package pl.jakub.travelorganizer.exceptions;

public class BadTripData extends RuntimeException{

    public BadTripData(String message) {
        super(message);
    }
}
