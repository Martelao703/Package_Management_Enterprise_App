package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.PackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageTypeDTO implements Serializable {
    @Id
    private String id;
    private String name;

    public PackageTypeDTO() {
    }

    public PackageTypeDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PackageTypeDTO from(PackageType packageType) {
        return new PackageTypeDTO(
                packageType.getId(),
                packageType.getName()
        );
    }

    public static List<PackageTypeDTO> from(List<PackageType> packageTypes) {
        return packageTypes.stream().map(PackageTypeDTO::from).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
