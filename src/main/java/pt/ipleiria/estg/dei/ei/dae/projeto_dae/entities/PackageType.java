package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "PackageType",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageTypes",
                query = "SELECT p FROM PackageType p ORDER BY p.name" // JPQL
        )
})
public class PackageType implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    private String name;
    @ManyToMany(mappedBy = "packageTypes")
    private List<Package> packages;

    public PackageType() {
    }

    public PackageType(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.packages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public List<Package> getPackages()
    {
        return packages;
    }

    public void setPackages(List<Package> packages)
    {
        this.packages = packages;
    }

    public void addPackage(Package p)
    {
        this.packages.add(p);
    }

    public void removePackage(Package p)
    {
        this.packages.remove(p);
    }
}
