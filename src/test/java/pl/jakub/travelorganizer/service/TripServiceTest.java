package pl.jakub.travelorganizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.exceptions.BadTripData;
import pl.jakub.travelorganizer.exceptions.ClientAlreadyAssigned;
import pl.jakub.travelorganizer.exceptions.TripNotFound;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.repository.ClientTripRepo;
import pl.jakub.travelorganizer.repository.TripRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TripServiceTest {

    @Mock
    private TripRepo tripRepo;

    @Mock
    private GuideService guideService;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientTripRepo clientTripRepo;

    @InjectMocks
    private TripService tripService;

    private Trip trip;
    private Guide guide;

    @BeforeEach
    void setUp(){
        guide = new Guide();
        guide.setId(1L);
        guide.setLastName("Nowak");
        guide.setFirstName("Adam");

        trip = new Trip();
        trip.setDateOfDeparture(LocalDate.now().plusDays(1));
        trip.setDateOfReturn(trip.getDateOfDeparture().plusDays(3));
        trip.setDestiny("Japan");
        trip.setSuggestedPrice(BigDecimal.valueOf(2000));
        trip.setId(1L);
    }

    @Test
    void when_addNewTrip_then_returnTrip(){
        //given
        when(guideService.findGuideById(1L)).thenReturn(guide);
        when(tripRepo.save(trip)).thenReturn(trip);
        //when
        Trip newTrip = tripService.addNewTrip(trip, 1L);
        //then
        assertThat(newTrip).isEqualTo(trip);
    }

    @Test
    void when_addNewTripWithNull_then_throw_BadTripData(){
        //given
        trip = null;
        //when
        BadTripData exception = assertThrows(BadTripData.class, () -> tripService.addNewTrip(trip,1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("trip cannot be null");
    }

    @Test
    void when_addNewTripWithBadDesteni_then_throw_BadTripData(){
        //given
        trip.setDestiny("");
        //when
        BadTripData exception = assertThrows(BadTripData.class, () -> tripService.addNewTrip(trip,1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("destiny cannot be empty");
    }

    @Test
    void when_addNewTripWithBadDepartureDate_then_throw_BadTripData(){
        //given
        LocalDate dateInPast = LocalDate.now().minusDays(2);
        trip.setDateOfDeparture(dateInPast);
        //when
        BadTripData exception = assertThrows(BadTripData.class, () -> tripService.addNewTrip(trip,1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("departure time cannot be in the past");
    }

    @Test
    void when_addNewTripWithBadReturnDate_then_throw_BadTripData(){
        //given
        LocalDate dateInPast = trip.getDateOfDeparture().minusDays(1);
        trip.setDateOfReturn(dateInPast);
        //when
        BadTripData exception = assertThrows(BadTripData.class, () -> tripService.addNewTrip(trip,1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("return date cannot be before return date");
    }

    @Test
    void when_addNewTripWithBadSuggestetPrice_then_throw_BadTripData(){
        //given
        trip.setSuggestedPrice(BigDecimal.valueOf(0));
        //when
        BadTripData exception = assertThrows(BadTripData.class, () -> tripService.addNewTrip(trip,1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("suggested price cannot be less or equal 0.00");
    }

    @Test
    void when_getAllTrip_return_listOfTrips(){
        //given
        List<Trip> trips = Arrays.asList(trip, trip, trip);
        when(tripRepo.findAll()).thenReturn(trips);
        //when
        List<Trip> returnedTrips = tripService.getAllTrips();
        //then
        assertThat(returnedTrips).isEqualTo(trips);
    }

    @Test
    void when_getTripById_then_returnTrip(){
        //given
        when(tripRepo.findById(anyLong())).thenReturn(Optional.of(trip));
        //when
        Trip returnedTrip = tripService.getTripById(1L);
        //then
        assertThat(returnedTrip).isEqualTo(trip);
    }
    
    @Test
    void when_getTripById_then_trowTripNotFound(){
        //given
        when(tripRepo.findById(anyLong())).thenReturn(Optional.empty());
        //when
        TripNotFound exception = assertThrows(TripNotFound.class, () -> tripService.getTripById(1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("trip with id: " + 1L + " doesn't exists");
    }

    @Test
    void when_addClientToTrip_then_return_trip(){
        //given
        Client client = new Client();
        client.setPassportNumber("ADV");
        client.setFirstName("Adam");
        client.setLastName("Nowak");
        when(clientTripRepo.existsByClientAndTrip(any(),any())).thenReturn(false);
        when(clientService.getClientById(anyLong())).thenReturn(client);
        when(tripRepo.findById(any())).thenReturn(Optional.of(trip));
        //when
        Trip updatedTrip = tripService.addClientToTrip(1L, 1L, BigDecimal.valueOf(200));
        //then
        assertThat(updatedTrip).isNotNull();
    }

    @Test
    void when_addClientToTrip_then_throwClientAlreadyAssigned(){
        //given
        Client client = new Client();
        client.setPassportNumber("ADV");
        client.setFirstName("Adam");
        client.setLastName("Nowak");
        client.setId(1L);
        when(clientTripRepo.existsByClientAndTrip(any(),any())).thenReturn(true);
        when(clientService.getClientById(anyLong())).thenReturn(client);
        when(tripRepo.findById(any())).thenReturn(Optional.of(trip));
        //when
        ClientAlreadyAssigned exception = assertThrows(ClientAlreadyAssigned.class, () -> tripService.addClientToTrip(1L, 1L, BigDecimal.valueOf(200)));
        //then
        assertThat(exception.getMessage()).isEqualTo("Client id: " + client.getId() + "is already assigned to trip id: " + trip.getId());
    }
}
