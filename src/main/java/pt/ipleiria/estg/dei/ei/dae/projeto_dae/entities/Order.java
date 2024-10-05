package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "\"Order\""
        //name = "Orderx"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id" // JPQL
        )
})
public class Order implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String origin; //address
    private String destiny; //address
    @ManyToMany
    @JoinTable(
            name = "Order_Product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products; //vai criar uma join table order_product
    @OneToMany(mappedBy = "order")
    private List<OrderCheckpoint> orderCheckpoints;

    public Order() {
    }

    public Order(LocalDate startDate, LocalDate deliveryDate, String origin, String destiny) {
        this.id = UUID.randomUUID().toString();
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.origin = origin;
        this.destiny = destiny;
        this.products = new ArrayList<>();
        this.orderCheckpoints = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public List<OrderCheckpoint> getOrderCheckpoints() {
        return orderCheckpoints;
    }

    public void addOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        this.orderCheckpoints.add(orderCheckpoint);
    }

    public void removeOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        this.orderCheckpoints.remove(orderCheckpoint);
    }
}
