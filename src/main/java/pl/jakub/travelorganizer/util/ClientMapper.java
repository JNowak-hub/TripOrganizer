package pl.jakub.travelorganizer.util;

import org.springframework.stereotype.Component;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;

@Component
public class ClientMapper {

    public Client updateClient(ClientRequest request, Client clientToUpdate){
        if(request.getFirstName() != null){
            clientToUpdate.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null){
            clientToUpdate.setLastName(request.getLastName());
        }
        if(request.getPassportNumber() != null){
            clientToUpdate.setPassportNumber(request.getPassportNumber());
        }
        return clientToUpdate;
    }
}
