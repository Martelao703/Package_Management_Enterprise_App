package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "Manufacturer",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllManufacturers",
                query = "SELECT m FROM Manufacturer m ORDER BY m.name" // JPQL
        ),

})
public class Manufacturer implements Serializable
{
    @Version
    private int version;
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "manufacturer")
    private List<ProductEntry> productEntries;

    public Manufacturer(){}

    public Manufacturer(String name)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.productEntries = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<ProductEntry> getProductEntries()
    {
        return productEntries;
    }

    public void setProductEntries(List<ProductEntry> productEntries)
    {
        this.productEntries = productEntries;
    }

    public void addProductEntry(ProductEntry productEntry)
    {
        this.productEntries.add(productEntry);
    }

    public void removeProductEntry(ProductEntry productEntry)
    {
        this.productEntries.remove(productEntry);
    }
}
