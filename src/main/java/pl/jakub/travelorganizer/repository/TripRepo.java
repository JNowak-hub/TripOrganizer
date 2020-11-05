package pl.jakub.travelorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakub.travelorganizer.model.Trip;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {
}
