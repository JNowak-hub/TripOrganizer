package pl.jakub.travelorganizer.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.request.ClientRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientMapperTest {

    private ClientMapper clientMapper = new ClientMapper();

    private Client client;

    @BeforeEach
    void setUp(){
        client = new Client();
        client.setFirstName("Adam");
        client.setLastName("Kowalski");
        client.setPassportNumber("A232AVW23231AC32332");
    }

    @Test
    void when_updateClient_then_updateAllFields(){
        //given
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Kuba");
        clientRequest.setLastName("Nowak");
        clientRequest.setPassportNumber("123123123");
        //when
        Client updatedClient = clientMapper.updateClient(clientRequest, client);
        //then
        assertThat(client.getFirstName()).isEqualTo(clientRequest.getFirstName());
        assertThat(client.getLastName()).isEqualTo(clientRequest.getLastName());
        assertThat(client.getPassportNumber()).isEqualTo(clientRequest.getPassportNumber());
    }
    
    @Test
    void when_updateClient_then_updateFirstNameOnly(){
        //given
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setFirstName("Kuba");
        //when
        Client updatedClient = clientMapper.updateClient(clientRequest, client);
        //then
        assertThat(client.getFirstName()).isEqualTo(clientRequest.getFirstName());
        assertThat(client.getLastName()).isEqualTo("Kowalski");
        assertThat(client.getPassportNumber()).isEqualTo("A232AVW23231AC32332");
    }

    @Test
    void when_updateClient_then_updateLastNameOnly(){
        //given
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setLastName("Nowak");
        //when
        Client updatedClient = clientMapper.updateClient(clientRequest, client);
        //then
        assertThat(client.getFirstName()).isEqualTo("Adam");
        assertThat(client.getLastName()).isEqualTo(clientRequest.getLastName());
        assertThat(client.getPassportNumber()).isEqualTo("A232AVW23231AC32332");
    }

    @Test
    void when_updateClient_then_updatePassportNumberOnly(){
        //given
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setPassportNumber("ABC123VCAs");
        //when
        Client updatedClient = clientMapper.updateClient(clientRequest, client);
        //then
        assertThat(client.getFirstName()).isEqualTo("Adam");
        assertThat(client.getLastName()).isEqualTo("Kowalski");
        assertThat(client.getPassportNumber()).isEqualTo(clientRequest.getPassportNumber());
    }
}
