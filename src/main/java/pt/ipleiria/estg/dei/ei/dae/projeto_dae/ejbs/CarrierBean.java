package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Carrier;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class CarrierBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.id) FROM Carrier c WHERE c.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsName(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.name) FROM Carrier c WHERE c.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public Carrier create(String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (existsName(name)) {
            throw new MyEntityExistsException("Carrier with name '" + name + "' already exists");
        }

        Carrier carrier = null;
        try {
            carrier = new Carrier(name);
            entityManager.persist(carrier);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return carrier;
    }

    public List<Carrier> getAll() {
        return entityManager.createNamedQuery("getAllCarriers", Carrier.class).getResultList();
    }

    public Carrier find(String id) {
        return entityManager.find(Carrier.class, id);
    }

    public Carrier update(String id, String newName) throws MyEntityNotFoundException, MyConstraintViolationException,
            MyEntityExistsException {
        Carrier carrier = find(id);
        if (carrier == null) {
            throw new MyEntityNotFoundException("Carrier with id '" + id + "' not found");
        }

        if (existsName(newName)) {
            throw new MyEntityExistsException("Carrier with name '" + newName + "' already exists");
        }

        try {
            entityManager.lock(carrier, LockModeType.OPTIMISTIC);
            carrier.setName(newName);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return carrier;
    }

    public void delete(String id) throws MyEntityNotFoundException {
        Carrier carrierToRemove = find(id);
        if (carrierToRemove != null) {
            entityManager.remove(carrierToRemove);
        } else {
            throw new MyEntityNotFoundException("Carrier with id '" + id + "' not found");
        }
    }
}
