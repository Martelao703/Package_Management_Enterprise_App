package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "Carrier",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllCarriers",
                query = "SELECT c FROM Carrier c ORDER BY c.name" // JPQL
        )
})
public class Carrier implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "carrier")
    private List<OrderCheckpoint> orderCheckpoints;

    public Carrier() {
    }

    public Carrier(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.orderCheckpoints = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderCheckpoint> getOrderCheckpoints() {
        return orderCheckpoints;
    }

    public void setOrderCheckpoints(List<OrderCheckpoint> orderCheckpoints) {
        this.orderCheckpoints = orderCheckpoints;
    }

    public void addOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        this.orderCheckpoints.add(orderCheckpoint);
    }

    public void removeOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        this.orderCheckpoints.remove(orderCheckpoint);
    }
}
