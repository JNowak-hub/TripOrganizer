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
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.service.GuideService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class GuideControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GuideService guideService;
    @InjectMocks
    private GuideController controller;

    @BeforeEach
    void mockSetup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void when_getGuides_then_returnListOfGuides() throws Exception {
        Guide guide1 = new Guide();
        guide1.setLastName("Nowak");
        guide1.setFirstName("Adam");
        Guide guide2 = new Guide();
        guide2.setFirstName("Mikołaj");
        guide2.setLastName("Kopernik");

        List<Guide> guideList = Arrays.asList(
                guide1, guide2
        );

        when(guideService.getAllGuides()).thenReturn(guideList);

        mockMvc.perform(get("/guide/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", is(guide1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is(guide1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", is(guide2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", is(guide2.getFirstName())));
    }

    @Test
    void when_addGuide_then_returnAddedGuide() throws Exception {
        Guide guideToAdd = new Guide();
        guideToAdd.setLastName("Nowak");
        guideToAdd.setFirstName("Adam");

        ObjectMapper objectMapper = new ObjectMapper();

        when(guideService.addNewGuide(any())).thenReturn(guideToAdd);

        mockMvc.perform(post("/guide")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(guideToAdd)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(guideToAdd.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is(guideToAdd.getLastName())));
    }

    @Test
    void when_addGuide_then_returnStatus400() throws Exception {
        Guide guideToAdd = new Guide();

        ObjectMapper objectMapper = new ObjectMapper();

        when(guideService.addNewGuide(any())).thenThrow(BadGuideData.class);

        mockMvc.perform(post("/guide")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(guideToAdd)))
                .andExpect(status().isBadRequest());
    }

}
