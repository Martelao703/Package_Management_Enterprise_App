package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class ProductBean
{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM Product p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Product create(String productEntryId, Float quantityLeft, LocalDate validityDate)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        ProductEntry productEntry = entityManager.find(ProductEntry.class, productEntryId);
        if (productEntry == null) {
            throw new MyEntityNotFoundException("ProductEntry with id '" + productEntryId + "' not found");
        }

        Product product = null;
        try {
            product = new Product(productEntry, quantityLeft, validityDate);
            entityManager.persist(product);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return product;
    }

    public List<Product> getAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(String id) {
        return entityManager.find(Product.class, id);
    }

    public Product update(String id, Float newQuantityLeft, LocalDate newValidityDate)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Product product = find(id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id '" + id + "' not found");
        }

        try {
            entityManager.lock(product, LockModeType.OPTIMISTIC);
            product.setQuantityLeft(newQuantityLeft);
            product.setValidityDate(newValidityDate);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return product;
    }

    public void delete(String id) throws MyEntityNotFoundException{
        Product productToRemove = find(id);
        if (productToRemove != null) {
            entityManager.remove(productToRemove);
        } else {
            throw new MyEntityNotFoundException("Product with id '" + id + "' not found");
        }
    }
}
