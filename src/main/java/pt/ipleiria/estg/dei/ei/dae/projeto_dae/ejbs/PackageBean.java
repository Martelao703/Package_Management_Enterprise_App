package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.exceptions.MyEntityNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM Package p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Package create(String materials, int isRecyclable, LocalDate fabricationDate) throws MyConstraintViolationException {
        Package package_ = null;
        try {
            package_ = new Package(materials, isRecyclable, fabricationDate);
            entityManager.persist(package_);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        entityManager.flush();
        return package_;
    }

    public List<Package> getAll() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(String id) {
        return entityManager.find(Package.class, id);
    }

    public Package update(String id, String newMaterials, int newIsRecyclable, LocalDate newFabricationDate)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        Package package_ = find(id);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with name '" + id + "' not found");
        }

        try {
            entityManager.lock(package_, LockModeType.OPTIMISTIC);
            package_.setMaterials(newMaterials);
            package_.setIsRecyclable(newIsRecyclable);
            package_.setFabricationDate(newFabricationDate);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        entityManager.flush();
        return package_;
    }

    public void delete(String id) throws MyEntityNotFoundException {
        Package packageToRemove = find(id);
        if (packageToRemove != null) {
            entityManager.remove(packageToRemove);
        } else {
            throw new MyEntityNotFoundException("Package with id '" + id + "' not found");
        }
    }

    public void addPackageType(String packageID, String packageTypeId) throws MyEntityNotFoundException, Exception {
        var package_ = entityManager.find(Package.class, packageID);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with id '" + packageID + "' not found");
        }
        var packageType = entityManager.find(PackageType.class, packageTypeId);
        if (packageType == null) {
            throw new MyEntityNotFoundException("PackageType with id '" + packageTypeId + "' not found");
        }
        if (package_.getPackageTypes().contains(packageType)) {
            throw new Exception("PackageType with id '" + packageTypeId + "' already exists");
        }
        package_.addType(packageType);
    }

    public void removePackageType(String packageID, String packageTypeId) throws MyEntityNotFoundException, Exception {
        var package_ = entityManager.find(Package.class, packageID);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with id '" + packageID + "' not found");
        }
        var packageType = entityManager.find(PackageType.class, packageTypeId);
        if (packageType == null) {
            throw new MyEntityNotFoundException("PackageType with id '" + packageTypeId + "' not found");
        }
        if (!package_.getPackageTypes().contains(packageType)) {
            throw new Exception("PackageType with id '" + packageTypeId + "' does not exist");
        }
        package_.removeType(packageType);
    }

}
