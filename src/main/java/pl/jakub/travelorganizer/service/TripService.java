package pl.jakub.travelorganizer.service;

import org.springframework.stereotype.Service;
import pl.jakub.travelorganizer.exceptions.BadTripData;
import pl.jakub.travelorganizer.exceptions.ClientAlreadyAssigned;
import pl.jakub.travelorganizer.exceptions.TripNotFound;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.Guide;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.model.clienttravel.ClientTrip;
import pl.jakub.travelorganizer.model.clienttravel.ClientTriplId;
import pl.jakub.travelorganizer.repository.ClientTripRepo;
import pl.jakub.travelorganizer.repository.TripRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private TripRepo tripRepo;
    private GuideService guideService;
    private ClientService clientService;
    private ClientTripRepo clientTripRepo;

    public TripService(TripRepo tripRepo, GuideService guideService, ClientService clientService, ClientTripRepo clientTripRepo) {
        this.tripRepo = tripRepo;
        this.guideService = guideService;
        this.clientService = clientService;
        this.clientTripRepo = clientTripRepo;
    }

    public List<Trip> getAllTrips(){
        return tripRepo.findAll();
    }

    public Trip getTripById(Long id){
        Optional<Trip> optionalTrip = tripRepo.findById(id);
        if(optionalTrip.isEmpty()){
            throw new TripNotFound("trip with id: " + id + " doesn't exists");
        }
        return optionalTrip.get();
    }

    public Trip addClientToTrip(Long tripId, Long clientId, BigDecimal finalPrice){
        Trip trip = getTripById(tripId);
        Client client = clientService.getClientById(clientId);

        if(clientTripRepo.existsByClientAndTrip(client, trip)){
            throw new ClientAlreadyAssigned("Client id: " + client.getId() + "is already assigned to trip id: " + trip.getId());
        }

        ClientTriplId clientTriplId = new ClientTriplId(client.getId(), trip.getId());
        ClientTrip clientTrip = new ClientTrip(clientTriplId, client, trip);
        clientTrip.setFinalPrice(finalPrice);

        clientTripRepo.save(clientTrip);
        trip.getClients().add(clientTrip);
        return trip;
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
