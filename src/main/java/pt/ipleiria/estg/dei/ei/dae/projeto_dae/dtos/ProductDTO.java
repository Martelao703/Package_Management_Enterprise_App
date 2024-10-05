package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Carrier;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {
    @Id
    private String id;
    private Float quantityLeft;
    private String productEntryId;
    private LocalDate validityDate;


    public ProductDTO() {
    }

<<<<<<< HEAD
    public ProductDTO(String productEntryId, Float quantityLeft, LocalDate validityDate) {
        this.id = UUID.randomUUID().toString();
=======
    public ProductDTO(String id, String productEntryId, Float quantityLeft, Date validityDate) {
        this.id = id;
>>>>>>> 561ca037c92a0b389e567e355d1f93e0b44a7a69
        this.productEntryId = productEntryId;
        this.quantityLeft = quantityLeft;
        this.validityDate = validityDate;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getProductEntry().getId(),
                product.getQuantityLeft(),
                product.getValidityDate()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(Float quantityLeft) {
        if (quantityLeft >= 0)
            this.quantityLeft = quantityLeft;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public String getProductEntryId() {
        return productEntryId;
    }

    public void setProductEntryId(String productEntryId) {
        this.productEntryId = productEntryId;
    }
}
