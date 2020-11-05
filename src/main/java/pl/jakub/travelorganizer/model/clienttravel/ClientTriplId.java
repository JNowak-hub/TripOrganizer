package pl.jakub.travelorganizer.model.clienttravel;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClientTriplId implements Serializable {

    private Long clientId;
    private Long tripId;

    public ClientTriplId() {};

    public ClientTriplId(Long clientId, Long tripId) {
        this.clientId = clientId;
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientTriplId that = (ClientTriplId) o;
        return Objects.equals(clientId, that.clientId) &&
                Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, tripId);
    }
}
