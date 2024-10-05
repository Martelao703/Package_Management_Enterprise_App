package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "ProductEntry",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProductEntries",
                query = "SELECT pe FROM ProductEntry pe ORDER BY pe.name" // JPQL
        )
})
public class ProductEntry implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    private Float weight; //in kg
    private String ingredients;
    @OneToMany(mappedBy = "productEntry")
    private List<Product> products;

    public ProductEntry() {
    }

    public ProductEntry(String name, ProductType productType, Float weight, Manufacturer manufacturer, String ingredients) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.weight = weight;
        this.productType = productType;
        this.manufacturer = manufacturer;
        this.ingredients = ingredients;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProducts(Product product) {
        this.products.add(product);
    }

    public void removeProducts(Product product) {
        this.products.remove(product);
    }
}
