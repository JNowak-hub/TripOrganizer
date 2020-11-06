package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadGuideData;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.repository.GuideRepo;

import java.util.List;

@Service
public class GuideService {

    private GuideRepo guideRepo;

    public GuideService(GuideRepo guideRepo) {
        this.guideRepo = guideRepo;
    }

    public List<Guide> getAllGuides() {
        return guideRepo.findAll();
    }

    public Guide addNewGuide(Guide guide) {
        if(!validateGuide(guide)){
            throw new BadGuideData("Check if all data is filled");
        }
        return guideRepo.save(guide);
    }

    private Boolean validateGuide(Guide guide) {
        if (guide == null) {
            return false;
        } else if (guide.getFirstName().isBlank() || guide.getFirstName().isEmpty()) {
            return false;
        } else if (guide.getLastName().isBlank() || guide.getLastName().isEmpty()) {
            return false;
        }
        return true;
    }
}
