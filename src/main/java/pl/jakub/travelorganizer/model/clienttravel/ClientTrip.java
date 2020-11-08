package pl.jakub.travelorganizer.model.clienttravel;

import pl.jakub.travelorganizer.model.Client;
import pl.jakub.travelorganizer.model.Trip;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ClientTrip {

    @EmbeddedId
    private ClientTriplId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId")
    private Client client;

    @ManyToOne
    @MapsId("tripId")
    private Trip trip;

    private BigDecimal finalPrice;

    public ClientTrip(ClientTriplId id, Client client, Trip trip) {
        this.id = new ClientTriplId(client.getId(), trip.getId());
        this.client = client;
        this.trip = trip;
    }

    public ClientTrip() {
    }

    public ClientTriplId getId() {
        return id;
    }

    public void setId(ClientTriplId id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
