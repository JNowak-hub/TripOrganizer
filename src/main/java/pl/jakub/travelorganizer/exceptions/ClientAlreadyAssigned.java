package pl.jakub.travelorganizer.exceptions;

public class ClientAlreadyAssigned extends RuntimeException{

    public ClientAlreadyAssigned(String message) {
        super(message);
    }

}
