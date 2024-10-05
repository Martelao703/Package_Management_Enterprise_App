package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(o.id) FROM Order o WHERE o.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Order create(LocalDate startDate, LocalDate deliveryDate, String origin, String destiny)
            throws MyConstraintViolationException {
        Order order = null;
        try {
            order = new Order(startDate, deliveryDate, origin, destiny);
            entityManager.persist(order);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return order;
    }

    public List<Order> getAll() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public Order find(String id) {
        return entityManager.find(Order.class, id);
    }

    public Order update(String id, LocalDate newStartDate, LocalDate newDeliveryDate, String newOrigin, String newDestiny)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Order order = find(id);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + id + "' not found");
        }

        try {
            entityManager.lock(order, LockModeType.OPTIMISTIC);
            order.setDeliveryDate(newStartDate);
            order.setDeliveryDate(newDeliveryDate);
            order.setOrigin(newOrigin);
            order.setDestiny(newDestiny);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return order;
    }

    public void delete(String id) throws MyEntityNotFoundException {
        Order orderToRemove = find(id);
        if (orderToRemove != null) {
            entityManager.remove(orderToRemove);
        } else {
            throw new MyEntityNotFoundException("Order with id '" + id + "' not found");
        }
    }

    public void addProduct(String orderID, String productID) throws MyEntityNotFoundException, Exception {
        Order order = entityManager.find(Order.class, orderID);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + orderID + "' not found");
        }
        Product product = entityManager.find(Product.class, productID);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id '" + productID + "' not found");
        }
        if (order.getProducts().contains(product)) {
            throw new Exception("Product with id '" + productID + "' already exists in order with id '" + orderID + "'");
        }
        order.addProduct(product);
    }

    public void removeProduct(String orderID, String productID) throws MyEntityNotFoundException, Exception {
        Order order = entityManager.find(Order.class, orderID);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + orderID + "' not found");
        }
        Product product = entityManager.find(Product.class, productID);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id '" + productID + "' not found");
        }
        if (!order.getProducts().contains(product)) {
            throw new Exception("Product with id '" + productID + "' not found in order with id '" + orderID + "'");
        }
        order.removeProduct(product);
    }

    public void setOrderDeliveryDateTime(String orderID, LocalDate datetime) throws MyEntityNotFoundException,
            Exception {
        if (datetime.isAfter(LocalDate.now())) {
            throw new Exception("Delivery date cannot be in the future");
        }
        Order order = entityManager.find(Order.class, orderID);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + orderID + "' not found");
        }
        if (order.getDeliveryDate() != null) {
            throw new Exception("Order with id '" + orderID + "' already has a delivery date");
        }
        order.setDeliveryDate(datetime);
    }
}
