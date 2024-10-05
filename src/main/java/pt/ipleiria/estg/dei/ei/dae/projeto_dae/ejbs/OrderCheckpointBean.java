package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class OrderCheckpointBean
{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(oc.id) FROM OrderCheckpoint oc WHERE oc.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public OrderCheckpoint create(String orderId, String packageId, String carrierId, String location, LocalDate datetime,
                          Float minTemperature, Float maxTemperature, Float minHumidity, Float maxHumidity,
                          Float minAtmPressure, Float maxAtmPressure, Float maxAcceleration, boolean wasPackageOpened)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + orderId + "' not found");
        }

        Package package_ = entityManager.find(Package.class, packageId);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with id '" + packageId + "' not found");
        }

        Carrier carrier = entityManager.find(Carrier.class, carrierId);
        if (carrier == null) {
            throw new MyEntityNotFoundException("Carrier with id '" + carrierId + "' not found");
        }

        OrderCheckpoint orderCheckpoint = null;
        try {
            orderCheckpoint = new OrderCheckpoint(order, package_, carrier, location, datetime, minTemperature, maxTemperature,
                    minHumidity, maxHumidity, minAtmPressure, maxAtmPressure, maxAcceleration, wasPackageOpened);
            entityManager.persist(orderCheckpoint);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return orderCheckpoint;
    }

    public List<OrderCheckpoint> getAll() {
        return entityManager.createNamedQuery("getAllOrderCheckpoints", OrderCheckpoint.class).getResultList();
    }

    public OrderCheckpoint find(String id) {
        return entityManager.find(OrderCheckpoint.class, id);
    }

    public OrderCheckpoint update(String id, String newLocation, LocalDate newDatetime, Float newMinTemperature,
                                  Float newMaxTemperature, Float newMinHumidity, Float newMaxHumidity, Float newMinAtmPressure,
                                  Float newMaxAtmPressure, Float newMaxAcceleration, boolean newWasPackageOpened)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        OrderCheckpoint orderCheckpoint = find(id);
        if (orderCheckpoint == null) {
            throw new MyEntityNotFoundException("OrderCheckpoint with id '" + id + "' not found");
        }

        try {
            entityManager.lock(orderCheckpoint, LockModeType.OPTIMISTIC);
            orderCheckpoint.setLocation(newLocation);
            orderCheckpoint.setDatetime(newDatetime);
            orderCheckpoint.setMinTemperature(newMinTemperature);
            orderCheckpoint.setMaxTemperature(newMaxTemperature);
            orderCheckpoint.setMinHumidity(newMinHumidity);
            orderCheckpoint.setMaxHumidity(newMaxHumidity);
            orderCheckpoint.setMinAtmPressure(newMinAtmPressure);
            orderCheckpoint.setMaxAtmPressure(newMaxAtmPressure);
            orderCheckpoint.setMaxAcceleration(newMaxAcceleration);
            orderCheckpoint.setWasPackageOpened(newWasPackageOpened);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return orderCheckpoint;
    }

    public void delete(String id) throws MyEntityNotFoundException{
        OrderCheckpoint orderCheckpointToRemove = find(id);
        if (orderCheckpointToRemove != null) {
            entityManager.remove(orderCheckpointToRemove);
        } else {
            throw new MyEntityNotFoundException("OrderCheckpoint with id '" + id + "' not found");
        }
    }
}
