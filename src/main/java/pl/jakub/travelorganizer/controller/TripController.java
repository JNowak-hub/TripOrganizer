package pl.jakub.travelorganizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.service.TripService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/trip")
@CrossOrigin("*")
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
    public ResponseEntity<Trip> addNewTrip(@RequestBody Trip trip, @PathVariable(name = "guideId") Long guideId){
        return ResponseEntity.ok(tripService.addNewTrip(trip,guideId));
    }

    @PostMapping("/client")
    public ResponseEntity<Trip> addClientToTrip(@RequestParam Long tripId, @RequestParam Long clientId, @RequestParam BigDecimal finalPrice){
        return ResponseEntity.ok(tripService.addClientToTrip(tripId,clientId,finalPrice));
    }

}
