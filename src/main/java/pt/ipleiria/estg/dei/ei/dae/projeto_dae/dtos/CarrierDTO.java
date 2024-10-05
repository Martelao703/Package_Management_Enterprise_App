package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Carrier;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CarrierDTO implements Serializable {
    @Id
    private String id;
    private String name;

    public CarrierDTO() {

    }

    public CarrierDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CarrierDTO from(Carrier carrier) {
        return new CarrierDTO(
                carrier.getId(),
                carrier.getName()
        );
    }

    public static List<CarrierDTO> from(List<Carrier> carriers) {
        return carriers.stream().map(CarrierDTO::from).collect(Collectors.toList());
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
