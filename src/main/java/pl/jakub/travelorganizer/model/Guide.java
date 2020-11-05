package pl.jakub.travelorganizer.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany
    private List<Trip> trips;
}
