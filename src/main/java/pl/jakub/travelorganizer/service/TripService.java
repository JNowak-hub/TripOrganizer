package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadTripData;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.repository.TripRepo;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TripService {

    private TripRepo tripRepo;
    private GuideService guideService;

    public TripService(TripRepo tripRepo, GuideService guideService) {
        this.tripRepo = tripRepo;
        this.guideService = guideService;
    }

    public Trip addNewTrip(Trip trip, Long guideId){
        Guide guide = guideService.findGuideById(guideId);
        validateTripData(trip);
        trip.setGuide(guide);

        return tripRepo.save(trip);
    }

    private void validateTripData(Trip trip){
        if (trip == null) {
            throw new BadTripData("trip cannot be null");
        } else if (trip.getDestiny().isBlank() || trip.getDestiny().isEmpty()) {
            throw new BadTripData("destiny cannot be empty");
        } else if (trip.getDateOfDeparture() == null || trip.getDateOfDeparture().isBefore(LocalDate.now())) {
            throw new BadTripData("departure time cannot be in the past");
        } else if(trip.getDateOfReturn() == null || trip.getDateOfReturn().isBefore(trip.getDateOfDeparture())){
            throw new BadTripData("return date cannot be before return date");
        } else if(trip.getSuggestedPrice() == null || trip.getSuggestedPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new BadTripData("suggested price cannot be less or equal 0.00");
        }
    }
}
