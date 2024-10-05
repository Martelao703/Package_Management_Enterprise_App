package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "Product"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.id" // JPQL
        )
})
@Inheritance(strategy = InheritanceType.JOINED)
public class Product implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    private Float quantityLeft; //between 0-1
    @ManyToOne
    @JoinColumn(name = "product_entry_id")
    private ProductEntry productEntry;
    private LocalDate validityDate;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    public Product() {
    }

    public Product(ProductEntry productEntry, Float quantityLeft, LocalDate validityDate) {
        this.id = UUID.randomUUID().toString();
        this.productEntry = productEntry;
        this.quantityLeft = quantityLeft;
        this.validityDate = validityDate;
        this.orders = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantityLeft(Float quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public Float getQuantityLeft() {
        return quantityLeft;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public ProductEntry getProductEntry() {
        return productEntry;
    }

    public void setProductEntry(ProductEntry productEntry) {
        this.productEntry = productEntry;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }
}
