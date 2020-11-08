package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadClientData;
import pl.jakub.travelorganizer.exceptions.ClientNotFound;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;
import pl.jakub.travelorganizer.repository.ClientRepo;
import pl.jakub.travelorganizer.util.ClientMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private ClientRepo clientRepo;
    private ClientMapper clientMapper;

    public ClientService(ClientRepo clientRepo, ClientMapper clientMapper) {
        this.clientRepo = clientRepo;
        this.clientMapper = clientMapper;
    }

    @Transactional
    public Client deleteClient(Long id){
        Client clientToDelete = getClientById(id);

        clientRepo.delete(clientToDelete);

        return clientToDelete;
    }

    @Transactional
    public Client updateClient(ClientRequest clientRequest, Long clientId){
        Client client = getClientById(clientId);

        clientMapper.updateClient(clientRequest, client);

        return  clientRepo.save(client);
    }

    public List<Client> getAllClients(){
        return clientRepo.findAll();
    }

    public Client getClientById(Long id){
        Optional<Client> optionalClient = clientRepo.findById(id);
        if(optionalClient.isEmpty()){
            throw new ClientNotFound("client with id: " + id + " doesn't exists");
        }
        return optionalClient.get();
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
