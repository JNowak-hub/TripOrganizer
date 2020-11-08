package pl.jakub.travelorganizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.exceptions.BadClientData;
import pl.jakub.travelorganizer.exceptions.ClientNotFound;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;
import pl.jakub.travelorganizer.repository.ClientRepo;
import pl.jakub.travelorganizer.util.ClientMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ClientServiceTest {

    @Mock
    private ClientRepo clientRepo;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void clientSetUp(){
        client = new Client();
        client.setFirstName("Adam");
        client.setLastName("Kowalski");
        client.setPassportNumber("A232AVW23231AC32332");
    }

    @Test
    void when_addClient_then_return_client(){
        //given
        when(clientRepo.save(client)).thenReturn(client);
        //when
        Client savedClient = clientService.addClient(client);
        //then
        assertThat(savedClient).isEqualTo(client);
    }

    @Test
    void when_addClient_with_badLastName_then_throw_BadClientData(){
        //given
        client.setLastName("");
        //when
        BadClientData exception = assertThrows(BadClientData.class , () -> clientService.addClient(client));
        //then
        assertThat(exception.getMessage()).isEqualTo("Check if all data is filled");
    }

    @Test
    void when_addClient_with_badFirstName_then_throw_BadClientData(){
        //given
        client.setFirstName("");
        //when
        BadClientData exception = assertThrows(BadClientData.class , () -> clientService.addClient(client));
        //then
        assertThat(exception.getMessage()).isEqualTo("Check if all data is filled");
    }

    @Test
    void when_addClient_with_badPassport_then_throw_BadClientData(){
        //given
        client.setPassportNumber("");
        //when
        BadClientData exception = assertThrows(BadClientData.class , () -> clientService.addClient(client));
        //then
        assertThat(exception.getMessage()).isEqualTo("Check if all data is filled");
    }

    @Test
    void when_getAllClient_then_return_listOfClients(){
        //given
        Client client1 = new Client();
        client1.setPassportNumber("adwdadad");
        client1.setFirstName("Adam");
        client1.setLastName("Nowak");

        Client client2 = new Client();
        client2.setPassportNumber("abcabcabc");
        client2.setFirstName("Mikolaj");
        client2.setLastName("Kopernik");

        List<Client> clientList = Arrays.asList(
                client, client1, client2
        );
        when(clientRepo.findAll()).thenReturn(clientList);
        //when
        List<Client> returnedClients = clientService.getAllClients();
        //then
        assertThat(returnedClients).isEqualTo(clientList);
    }

    @Test
    void when_getClientById_then_return_client(){
        //given
        when(clientRepo.findById(anyLong())).thenReturn(Optional.of(client));
        //when
        Client returnedClient = clientService.getClientById(1L);
        //then
        assertThat(returnedClient).isEqualTo(client);
    }

    @Test
    void when_getClientById_then_throwClientNotFound(){
        //given
        when(clientRepo.findById(anyLong())).thenReturn(Optional.empty());
        //then
        ClientNotFound exception = assertThrows(ClientNotFound.class, () -> clientService.getClientById(1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("client with id: " + 1 + " doesn't exists");
    }

    @Test
    void when_updateClient_then_returnUpdatedClient(){
        //given
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Mikołaj");
        when(clientMapper.updateClient(any(),any())).thenCallRealMethod();
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        //when
        clientService.updateClient(clientRequest, 1L);
        //then
        assertThat(client.getFirstName()).isEqualTo("Mikołaj");
    }

    @Test
    void when_deleteClient_then_returnDeletedClient(){
        //given
        when(clientRepo.findById(anyLong())).thenReturn(Optional.of(client));
        //when
        Client deletedClient = clientService.deleteClient(1L);
        //then
        verify(clientRepo).delete(client);
        assertThat(deletedClient).isEqualTo(client);
    }

    @Test
    void when_deleteClient_then_throwClientNotFound(){
        //given
        when(clientRepo.findById(anyLong())).thenReturn(Optional.empty());
        //when
        ClientNotFound exception = assertThrows(ClientNotFound.class, () -> clientService.deleteClient(1L));
        //then
        verify(clientRepo, never()).delete(any());
        assertThat(exception.getMessage()).isEqualTo("client with id: " + 1 + " doesn't exists");
    }
}
