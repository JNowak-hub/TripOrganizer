package pl.jakub.travelorganizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {
    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips(){
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @PostMapping("/{guideId}")
    public ResponseEntity<Trip> addNewTrip(@RequestBody Trip trip, @PathVariable Long guideId){
        return ResponseEntity.ok(tripService.addNewTrip(trip,guideId));
    }

}
