package pl.jakub.travelorganizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.service.GuideService;

import java.util.List;

@RestController
@RequestMapping("/guide")
public class GuideController {

    private GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Guide>> getAllGuides(){
        return ResponseEntity.ok(guideService.getAllGuides());
    }

    @PostMapping
    public ResponseEntity<Guide> addGuide(@RequestBody Guide guide){
        return ResponseEntity.ok(guideService.addNewGuide(guide));
    }
}
