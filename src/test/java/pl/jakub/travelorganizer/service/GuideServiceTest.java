package pl.jakub.travelorganizer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jakub.travelorganizer.exceptions.BadGuideData;
import pl.jakub.travelorganizer.exceptions.GuideNotFound;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.repository.GuideRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class GuideServiceTest {

    @Mock
    private GuideRepo guideRepo;

    @InjectMocks
    private GuideService guideService;

    private Guide guide;

    @BeforeEach
    void setUp(){
        guide = new Guide();
        guide.setFirstName("Adam");
        guide.setLastName("Kowalczyk");
        guide.setId(1L);
    }

    @Test
    void when_getAllGuides_then_returnListOfGuides(){
        //given
        Guide guide1 = new Guide();
        guide1.setLastName("Nowak");
        guide1.setFirstName("Adam");
        Guide guide2 = new Guide();
        guide2.setFirstName("Mikołaj");
        guide2.setLastName("Kopernik");
        List<Guide> guides= Arrays.asList(guide, guide1, guide2);
        when(guideRepo.findAll()).thenReturn(guides);
        //when
        List<Guide> returnedGuides = guideService.getAllGuides();
        //then
        assertThat(returnedGuides).isEqualTo(guides);
    }

    @Test
    void when_addNewGuide_then_returnAddedGuide(){
        //given
        when(guideRepo.save(guide)).thenReturn(guide);
        //when
        Guide returnedGuide = guideService.addNewGuide(guide);
        //then
        assertThat(returnedGuide).isEqualTo(guide);
    }

    @Test
    void when_addNewGuide_withBadFirstName_then_throwBadGuideDataException(){
        //given
        when(guideRepo.save(guide)).thenReturn(guide);
        guide.setFirstName("");
        //when
        BadGuideData exception = assertThrows(BadGuideData.class, () -> guideService.addNewGuide(guide));
        //then
        assertThat(exception.getMessage()).isEqualTo("Check if all data is filled");
    }

    @Test
    void when_addNewGuide_withBadLastName_then_throwBadGuideDataException(){
        //given
        when(guideRepo.save(guide)).thenReturn(guide);
        guide.setLastName("");
        //when
        BadGuideData exception = assertThrows(BadGuideData.class, () -> guideService.addNewGuide(guide));
        //then
        assertThat(exception.getMessage()).isEqualTo("Check if all data is filled");
    }

    @Test
    void when_findGuideById_then_returnGuide(){
        //given
        when(guideRepo.findById(guide.getId())).thenReturn(Optional.ofNullable(guide));
        //when
        Guide foundGuide = guideService.findGuideById(1L);
        //then
        assertThat(foundGuide).isEqualTo(guide);
    }

    @Test
    void when_findGuideById_then_throwGuideNotFoundException(){
        //given
        when(guideRepo.findById(any())).thenReturn(Optional.empty());
        //when
        GuideNotFound exception = assertThrows(GuideNotFound.class, () -> guideService.findGuideById(1L));
        //then
        assertThat(exception.getMessage()).isEqualTo("Guide with id: " + 1 + " not found");
    }
}
