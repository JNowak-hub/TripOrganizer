package pl.jakub.travelorganizer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.jakub.travelorganizer.model.clienttravel.ClientTrip;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destiny;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfDeparture;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfReturn;
    private BigDecimal suggestedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @OneToMany(
            mappedBy = "trip",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ClientTrip> clients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public List<ClientTrip> getClients() {
        return clients;
    }

    public void setClients(List<ClientTrip> clients) {
        this.clients = clients;
    }
}
