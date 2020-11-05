package pl.jakub.travelorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakub.travelorganizer.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
}
