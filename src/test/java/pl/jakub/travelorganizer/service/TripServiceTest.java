package pl.jakub.travelorganizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.exceptions.BadTripData;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.repository.TripRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TripServiceTest {

    @Mock
    private TripRepo tripRepo;

    @Mock
    private GuideService guideService;

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
}
