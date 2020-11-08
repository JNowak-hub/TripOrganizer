package pl.jakub.travelorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.Trip;
import pl.jakub.travelorganizer.model.clienttravel.ClientTrip;
import pl.jakub.travelorganizer.model.clienttravel.ClientTriplId;

@Repository
public interface ClientTripRepo extends JpaRepository<ClientTrip, ClientTriplId> {

    Boolean existsByClientAndTrip(Client client, Trip trip);
}
