package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "Package",
        uniqueConstraints = @UniqueConstraint(columnNames = {"fabrication_date", "materials"}) //se 10000 embalagens forem feitas so existe uma row
)
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
public class Package implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    @NotNull
    private String materials;
    @NotNull
    @Column(name = "is_recyclable")
    private int isRecyclable; //1-YES 0-NO
    @NotNull
    @Column(name = "fabrication_date")
    private LocalDate fabricationDate;
    @ManyToMany
    @JoinTable(
            name = "Package_PackageType",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "package_type_id")
    )
    private List<PackageType> packageTypes;
    @OneToMany(mappedBy = "package_")
    private List<OrderCheckpoint> orderCheckpoints;

    public Package() {
    }

    public Package(String materials, int isRecyclable, LocalDate fabricationDate) {
        this.id = UUID.randomUUID().toString();
        this.materials = materials;
        this.isRecyclable = isRecyclable;
        this.fabricationDate = fabricationDate;
        this.packageTypes = new ArrayList<>();
        this.orderCheckpoints = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PackageType> getPackageTypes() {
        return packageTypes;
    }

    public void setPackageTypes(List<PackageType> packageTypes) {
        this.packageTypes = packageTypes;
    }

    public void addType(PackageType packageType) {
        packageTypes.add(packageType);
    }

    public void removeType(PackageType packageType) {
        packageTypes.remove(packageType);
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public boolean isRecyclable() {
        return isRecyclable == 1;
    }

    public int getIsRecyclable() {
        return isRecyclable;
    }

    public void setIsRecyclable(int isRecyclable) {
        this.isRecyclable = isRecyclable;
    }

    public LocalDate getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(LocalDate fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public List<OrderCheckpoint> getOrderCheckpoints() {
        return orderCheckpoints;
    }

    public void setOrderCheckpoints(List<OrderCheckpoint> orderCheckpoints) {
        this.orderCheckpoints = orderCheckpoints;
    }

    public void addOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        orderCheckpoints.add(orderCheckpoint);
    }

    public void removeOrderCheckpoint(OrderCheckpoint orderCheckpoint) {
        orderCheckpoints.remove(orderCheckpoint);
    }

    public void addPackageType(PackageType packageType) {
        packageTypes.add(packageType);
    }

    public void removePackageType(PackageType packageType) {
        packageTypes.remove(packageType);
    }
}
