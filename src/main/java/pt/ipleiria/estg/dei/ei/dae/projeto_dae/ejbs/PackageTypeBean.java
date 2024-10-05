package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM PackageType p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsName(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.name) FROM PackageType p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public PackageType create(String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(name)) {
            throw new MyEntityExistsException("PackageType with name '" + name + "' already exists");
        }

        PackageType packageType = null;
        try {
            packageType = new PackageType(name);
            entityManager.persist(packageType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return packageType;
    }

    public List<PackageType> getAll() {
        return entityManager.createNamedQuery("getAllPackageTypes", PackageType.class).getResultList();
    }

    public PackageType find(String id) {
        return entityManager.find(PackageType.class, id);
    }

    public PackageType update(String id, String newName) throws MyEntityNotFoundException, MyConstraintViolationException,
            MyEntityExistsException {
        PackageType packageType = find(id);
        if (packageType == null) {
            throw new MyEntityNotFoundException("PackageType with id '" + id + "' not found");
        }

        if (existsName(newName)) {
            throw new MyEntityExistsException("PackageType with name '" + newName + "' already exists");
        }

        try {
            entityManager.lock(packageType, LockModeType.OPTIMISTIC);
            packageType.setName(newName);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return packageType;
    }

    public void delete(String id) throws MyEntityNotFoundException {
        PackageType packageTypeToRemove = find(id);
        if (packageTypeToRemove != null) {
            entityManager.remove(packageTypeToRemove);
        } else {
            throw new MyEntityNotFoundException("PackageType with id '" + id + "' not found");
        }
    }
}
