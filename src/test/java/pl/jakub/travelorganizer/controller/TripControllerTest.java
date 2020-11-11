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
import pl.jakub.travelorganizer.exceptions.BadGuideData;
import pl.jakub.travelorganizer.exceptions.BadTripData;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.service.GuideService;
import pl.jakub.travelorganizer.service.TripService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class TripControllerTest {
    private MockMvc mockMvc;
    
    @Mock
    private TripService tripService;
    
    @InjectMocks
    private TripController controller;

    @BeforeEach
    void mockSetup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }
    
    @Test
    void when_getAllTrips_return_listOfTrips() throws Exception {
        Trip trip = new Trip();
        trip.setDestiny("Katowice");
        trip.setSuggestedPrice(BigDecimal.valueOf(10));
        trip.setDateOfDeparture(LocalDate.now().plusDays(2));
        Trip trip2 = new Trip();
        trip2.setDestiny("Sosnowiec");
        trip2.setSuggestedPrice(BigDecimal.valueOf(20));
        trip2.setDateOfDeparture(LocalDate.now().plusDays(5));
        List<Trip> tripList = Arrays.asList(
                trip, trip2
        );

        when(tripService.getAllTrips()).thenReturn(tripList);

        mockMvc.perform(get("/trip/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destiny", is(trip.getDestiny())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].suggestedPrice", is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfDeparture", is(trip.getDateOfDeparture().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].destiny", is(trip2.getDestiny())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].suggestedPrice", is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateOfDeparture", is(trip2.getDateOfDeparture().toString())));
    }

    @Test
    void when_addNewTrip_return_status400() throws Exception {
        Trip trip = new Trip();
        trip.setDestiny("Katowice");
        trip.setSuggestedPrice(BigDecimal.valueOf(10));
        trip.setDateOfDeparture(LocalDate.now().plusDays(2));

        when(tripService.addNewTrip(any(), anyLong())).thenThrow(BadTripData.class);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/trip/{guideId}", "0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isBadRequest());
    }
}
