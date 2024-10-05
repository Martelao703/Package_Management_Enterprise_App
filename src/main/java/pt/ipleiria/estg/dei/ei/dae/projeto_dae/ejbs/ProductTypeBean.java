package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductTypeBean
{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM ProductType p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsName(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.name) FROM ProductType p WHERE p.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public ProductType create(String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (existsName(name)) {
            throw new MyEntityExistsException("ProductType with name '" + name + "' already exists");
        }

        ProductType productType = null;
        try {
            productType = new ProductType(name);
            entityManager.persist(productType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return productType;
    }

    public List<ProductType> getAll() {
        return entityManager.createNamedQuery("getAllProductTypes", ProductType.class).getResultList();
    }

    public ProductType find(String id) {
        return entityManager.find(ProductType.class, id);
    }

    public ProductType update(String id, String newName) throws MyEntityNotFoundException, MyConstraintViolationException,
            MyEntityExistsException{
        ProductType productType = find(id);
        if (productType == null) {
            throw new MyEntityNotFoundException("ProductType with id '" + id + "' not found");
        }

        if (existsName(newName)) {
            throw new MyEntityExistsException("ProductType with name '" + newName + "' already exists");
        }

        try {
            entityManager.lock(productType, LockModeType.OPTIMISTIC);
            productType.setName(newName);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return productType;
    }

    public void delete(String id) throws MyEntityNotFoundException{
        ProductType productTypeToRemove = find(id);
        if (productTypeToRemove != null) {
            entityManager.remove(productTypeToRemove);
        } else {
            throw new MyEntityNotFoundException("ProductType with id '" + id + "' not found");
        }
    }
}
