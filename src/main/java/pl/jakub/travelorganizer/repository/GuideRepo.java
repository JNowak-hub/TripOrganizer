package pl.jakub.travelorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jakub.travelorganizer.model.Guide;

@Repository
public interface GuideRepo extends JpaRepository<Guide, Long> {
}
