package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadClientData;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.repository.ClientRepo;

import java.util.List;

@Service
public class ClientService {

    private ClientRepo clientRepo;

    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client addClient(Client client){
        if(!validateClient(client)){
            throw new BadClientData("Check if all data is filled");
        }
        return clientRepo.save(client);
    }

    private Boolean validateClient(Client client){
        if(client == null){
            return false;
        } else if(client.getFirstName().isBlank() || client.getFirstName().isEmpty()){
            return false;
        } else if(client.getLastName().isBlank() || client.getLastName().isEmpty()){
            return false;
        } else if(client.getPassportNumber().isBlank() || client.getPassportNumber().isEmpty()){
            return false;
        }
        return true;
    }
}
