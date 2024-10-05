package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ProductEntryBean
{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(pe.id) FROM ProductEntry pe WHERE pe.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public boolean existsName(String name) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(pe.name) FROM ProductEntry pe WHERE pe.name = :name",
                Long.class
        );
        query.setParameter("name", name);
        return (Long) query.getSingleResult() > 0L;
    }

    public ProductEntry create(String name, String productTypeId, Float weight, String manufacturerId, String ingredients)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        ProductType productType = entityManager.find(ProductType.class, productTypeId);
        if (productType == null) {
            throw new MyEntityNotFoundException("ProductType with id '" + productTypeId + "' not found");
        }

        Manufacturer manufacturer = entityManager.find(Manufacturer.class, manufacturerId);
        if (manufacturer == null) {
            throw new MyEntityNotFoundException("Manufacturer with id '" + manufacturerId + "' not found");
        }

        ProductEntry productEntry = null;
        try {
            productEntry = new ProductEntry(name, productType, weight, manufacturer, ingredients);
            entityManager.persist(productEntry);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return productEntry;
    }

    public List<ProductEntry> getAll() {
        return entityManager.createNamedQuery("getAllProductEntries", ProductEntry.class).getResultList();
    }

    public ProductEntry find(String id) {
        return entityManager.find(ProductEntry.class, id);
    }

    public ProductEntry update(String id, String newName, Float newWeight, String newIngredients)
            throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        ProductEntry productEntry = find(id);
        if (productEntry == null) {
            throw new MyEntityNotFoundException("ProductEntry with id '" + id + "' not found");
        }

        if (existsName(newName)) {
            throw new MyEntityExistsException("ProductEntry with name '" + newName + "' already exists");
        }

        try {
            entityManager.lock(productEntry, LockModeType.OPTIMISTIC);
            productEntry.setName(newName);
            productEntry.setWeight(newWeight);
            productEntry.setIngredients(newIngredients);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return productEntry;
    }

    public void delete(String id) throws MyEntityNotFoundException{
        ProductEntry productEntryToRemove = find(id);
        if (productEntryToRemove != null) {
            entityManager.remove(productEntryToRemove);
        } else {
            throw new MyEntityNotFoundException("ProductEntry with id '" + id + "' not found");
        }
    }
}
