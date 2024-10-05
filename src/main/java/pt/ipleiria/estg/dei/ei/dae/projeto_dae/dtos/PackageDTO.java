package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.PackageType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PackageDTO implements Serializable {

    @Id
    private String id;
    private String materials;
    private int isRecyclable; //1-YES 0-NO
    private LocalDate fabricationDate;
    private List<PackageType> packageTypes;

    public PackageDTO() {
    }

<<<<<<< HEAD
    public PackageDTO(String materials, int isRecyclable, LocalDate fabricationDate, List<PackageType> packageTypes) {
        this.id = UUID.randomUUID().toString();
=======
    public PackageDTO(String id, String materials, int isRecyclable, Date fabricationDate, List<PackageType> packageTypes) {
        this.id = id;
>>>>>>> 561ca037c92a0b389e567e355d1f93e0b44a7a69
        this.materials = materials;
        this.isRecyclable = isRecyclable;
        this.fabricationDate = fabricationDate;
        this.packageTypes = packageTypes;
    }

    public static PackageDTO from(Package package_) {
        Hibernate.initialize(package_.getPackageTypes());
        return new PackageDTO(
                package_.getId(),
                package_.getMaterials(),
                package_.getIsRecyclable(),
                package_.getFabricationDate(),
                package_.getPackageTypes()
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(java.util.stream.Collectors.toList());
    }

    public static PackageDTO fromWithNoLists(Package package_) {
        return new PackageDTO(
                package_.getId(),
                package_.getMaterials(),
                package_.getIsRecyclable(),
                package_.getFabricationDate(),
                null
        );
    }

    public static List<PackageDTO> fromWithNoLists(List<Package> packages) {
        return packages.stream().map(PackageDTO::fromWithNoLists).collect(java.util.stream.Collectors.toList());
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

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
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

    public void addPackageType(PackageType packageType) {
        this.packageTypes.add(packageType);
    }

    public void removePackageType(PackageType packageType) {
        this.packageTypes.remove(packageType);
    }
}
