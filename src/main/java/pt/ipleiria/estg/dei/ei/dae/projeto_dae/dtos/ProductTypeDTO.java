package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductType;

import java.io.Serializable;
import java.util.List;

public class ProductTypeDTO implements Serializable {
    @Id
    private String id;
    private String name;

    public ProductTypeDTO() {
    }

    public ProductTypeDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ProductTypeDTO from(ProductType productType) {
        return new ProductTypeDTO(
                productType.getId(),
                productType.getName()
        );
    }

    public static List<ProductTypeDTO> from(List<ProductType> productTypes) {
        return productTypes.stream().map(ProductTypeDTO::from).collect(java.util.stream.Collectors.toList());
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
}
