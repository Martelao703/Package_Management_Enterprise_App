package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {
    @Id
    private String id;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private String origin;
    private String destiny;
    private List<Product> products;
    private List<OrderCheckpoint> orderCheckpoints;

    public OrderDTO() {
    }

<<<<<<< HEAD
    public OrderDTO(LocalDate startDate, LocalDate deliveryDate, String origin, String destiny,
=======
    public OrderDTO(String id, Date startDate, Date deliveryDate, String origin, String destiny,
>>>>>>> 561ca037c92a0b389e567e355d1f93e0b44a7a69
                    List<Product> products, List<OrderCheckpoint> orderCheckpoints) {
        this.id = id;
        this.id = UUID.randomUUID().toString();
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.origin = origin;
        this.destiny = destiny;
        this.products = products;
        this.orderCheckpoints = orderCheckpoints;
    }

    public static OrderDTO from(Order order) {
        Hibernate.initialize(order.getProducts());
        Hibernate.initialize(order.getOrderCheckpoints());
        return new OrderDTO(
                order.getId(),
                order.getStartDate(),
                order.getDeliveryDate(),
                order.getOrigin(),
                order.getDestiny(),
                order.getProducts(),
                order.getOrderCheckpoints()
        );
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }

    public static OrderDTO fromWithNoLists(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStartDate(),
                order.getDeliveryDate(),
                order.getOrigin(),
                order.getDestiny(),
                null,
                null
        );
    }

    public static List<OrderDTO> fromWithNoLists(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromWithNoLists).collect(Collectors.toList());
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

    public void addProducts(Product product) {
        this.products.add(product);
    }

    public void removeProducts(Product product) {
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

    public void setProducts(List<Product> products) {
        this.products = products;
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

