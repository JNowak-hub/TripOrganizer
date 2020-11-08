package pl.jakub.travelorganizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;
import pl.jakub.travelorganizer.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client){
        return ResponseEntity.ok(clientService.addClient(client));
    }

    @PatchMapping
    public ResponseEntity<Client> updateClient(@RequestBody ClientRequest clientRequest, @RequestParam Long clientId){
        return ResponseEntity.ok(clientService.updateClient(clientRequest, clientId));
    }

    @DeleteMapping
    public ResponseEntity<Client> deleteClient(@RequestParam Long clientId){
        return ResponseEntity.ok(clientService.deleteClient(clientId));
    }
}
