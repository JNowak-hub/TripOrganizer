package pl.jakub.travelorganizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.exceptions.BadGuideData;
import pl.jakub.travelorganizer.exceptions.GuideNotFound;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.repository.GuideRepo;
import pl.jakub.travelorganizer.repository.TripRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
}
