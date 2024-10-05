package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Carrier;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ManufacturerBean
{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(m.id) FROM Manufacturer m WHERE m.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsName(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(m.name) FROM Manufacturer m WHERE m.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public Manufacturer create(String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (existsName(name)) {
            throw new MyEntityExistsException("Manufacturer with name '" + name + "' already exists");
        }

        Manufacturer manufacturer = null;
        try {
            manufacturer = new Manufacturer(name);
            entityManager.persist(manufacturer);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return manufacturer;
    }

    public List<Manufacturer> getAll() {
        return entityManager.createNamedQuery("getAllManufacturers", Manufacturer.class).getResultList();
    }

    public Manufacturer find(String id) {
        return entityManager.find(Manufacturer.class, id);
    }

    public Manufacturer update(String id, String newName) throws MyEntityNotFoundException, MyConstraintViolationException,
            MyEntityExistsException{
        Manufacturer manufacturer = find(id);
        if (manufacturer == null) {
            throw new MyEntityNotFoundException("Manufacturer with id '" + id + "' not found");
        }

        if (existsName(newName)) {
            throw new MyEntityExistsException("Manufacturer with name '" + newName + "' already exists");
        }

        try {
            entityManager.lock(manufacturer, LockModeType.OPTIMISTIC);
            manufacturer.setName(newName);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return manufacturer;
    }

    public void delete(String id) throws MyEntityNotFoundException{
        Manufacturer manufacturerToRemove = find(id);
        if (manufacturerToRemove != null) {
            entityManager.remove(manufacturerToRemove);
        } else {
            throw new MyEntityNotFoundException("Manufacturer with id '" + id + "' not found");
        }
    }
}
