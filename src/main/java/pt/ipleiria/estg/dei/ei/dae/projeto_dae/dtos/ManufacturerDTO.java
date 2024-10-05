package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Manufacturer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerDTO implements Serializable {
    @Id
    private String id;
    private String name;

    public ManufacturerDTO() {
    }

    public ManufacturerDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ManufacturerDTO from(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getId(),
                manufacturer.getName()
        );
    }

    public static List<ManufacturerDTO> from(List<Manufacturer> manufacturers) {
        return manufacturers.stream().map(ManufacturerDTO::from).collect(Collectors.toList());
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
