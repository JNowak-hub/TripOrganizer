package pl.jakub.travelorganizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jakub.travelorganizer.configure.CustomExceptionHandler;
import pl.jakub.travelorganizer.exceptions.BadClientData;
import pl.jakub.travelorganizer.exceptions.ClientNotFound;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;
import pl.jakub.travelorganizer.service.ClientService;
import pl.jakub.travelorganizer.util.ClientMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientController controller;

    @BeforeEach
    void mockSetup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void when_getClients_then_returnListOfClients() throws Exception {
        Client client1 = new Client();
        client1.setPassportNumber("adwdadad");
        client1.setFirstName("Adam");
        client1.setLastName("Nowak");

        Client client2 = new Client();
        client2.setPassportNumber("abcabcabc");
        client2.setFirstName("Mikolaj");
        client2.setLastName("Kopernik");

        List<Client> clientList = Arrays.asList(
                client1, client2
        );

        when(clientService.getAllClients()).thenReturn(clientList);

        mockMvc.perform(get("/client/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", is(client1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is(client1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", is(client2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", is(client2.getFirstName())));
    }

    @Test
    void when_addClient_then_returnAddedClient() throws Exception {
        Client clientToAdd = new Client();
        clientToAdd.setLastName("lastName");
        clientToAdd.setFirstName("firstName");
        clientToAdd.setPassportNumber("passportNumber");

        ObjectMapper objectMapper = new ObjectMapper();

        when(clientService.addClient(any())).thenReturn(clientToAdd);

        mockMvc.perform(post("/client")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(clientToAdd)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(clientToAdd.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is(clientToAdd.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.passportNumber",is(clientToAdd.getPassportNumber())));
    }

    @Test
    void when_addClient_then_returnStatus409() throws Exception {
        Client clientToAdd = new Client();

        ObjectMapper objectMapper = new ObjectMapper();

        when(clientService.addClient(any())).thenThrow(BadClientData.class);

        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(clientToAdd)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void when_updateClient_then_returnStatus404() throws Exception {
        ClientRequest clientRequest = new ClientRequest();

        ObjectMapper objectMapper = new ObjectMapper();

        when(clientService.updateClient(any(),anyLong())).thenThrow(ClientNotFound.class);

        mockMvc.perform(patch("/client")
                .param("clientId", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void when_updateClient_then_returnUpdatedClient() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Kuba");
        clientRequest.setLastName("Kowalczyk");

        Client clientToUpdate = new Client();
        clientToUpdate.setLastName("Adam");
        clientToUpdate.setFirstName("Kaczmarczyk");
        ObjectMapper objectMapper = new ObjectMapper();

        ClientMapper clientMapper = new ClientMapper();
        clientMapper.updateClient(clientRequest, clientToUpdate);

        when(clientService.updateClient(any(),anyLong())).thenReturn(clientToUpdate);

        mockMvc.perform(patch("/client")
                .param("clientId", "1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(clientRequest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is(clientRequest.getLastName())));
    }

    @Test
    void when_deleteClient_then_returnDeletedClient() throws Exception {
        Client clientToDelete = new Client();
        clientToDelete.setFirstName("Kuba");
        clientToDelete.setLastName("Kowalczyk");

        ClientMapper clientMapper = new ClientMapper();

        when(clientService.deleteClient(anyLong())).thenReturn(clientToDelete);

        mockMvc.perform(delete("/client")
                .param("clientId", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(clientToDelete.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is(clientToDelete.getLastName())));
    }
    @Test
    void when_deleteClient_then_returnStatus400() throws Exception {
        Client clientToDelete = new Client();
        clientToDelete.setFirstName("Kuba");
        clientToDelete.setLastName("Kowalczyk");

        ClientMapper clientMapper = new ClientMapper();

        when(clientService.deleteClient(anyLong())).thenThrow(ClientNotFound.class);

        mockMvc.perform(delete("/client")
                .param("clientId", "1"))
                .andExpect(status().isNotFound());
    }

}
