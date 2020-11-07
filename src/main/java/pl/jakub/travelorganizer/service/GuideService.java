package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadGuideData;
import pl.jakub.travelorganizer.exceptions.GuideNotFound;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.repository.GuideRepo;

import java.util.List;
import java.util.Optional;

@Service
public class GuideService {

    private GuideRepo guideRepo;

    public GuideService(GuideRepo guideRepo) {
        this.guideRepo = guideRepo;
    }

    public Guide findGuideById(Long id){
        Optional<Guide> optionalGuide = guideRepo.findById(id);
        if(optionalGuide.isEmpty()){
            throw new GuideNotFound("Guide with id: " + id + " not found");
        }
        return optionalGuide.get();
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
