package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.ProductEntry;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ProductEntryDTO implements Serializable {

    @Id
    private String id;
    private String name;
    private String productTypeId;
    private String productTypeName;
    private Float weight;
    private String manufacturerId;
    private String manufacturerName;
    private String ingredients;
    private List<Product> products;


    public ProductEntryDTO() {
    }

    public ProductEntryDTO(String id, String name, String productTypeId, String productTypeName, Float weight, String manufacturerId, String manufacturerName, String ingredients,
                           List<Product> products) {
        this.id = id;
        this.name = name;
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
        this.weight = weight;
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.ingredients = ingredients;
        this.products = products;
    }

    public static ProductEntryDTO from(ProductEntry productEntry) {
        Hibernate.initialize(productEntry.getProducts());
        return new ProductEntryDTO(
                productEntry.getId(),
                productEntry.getName(),
                productEntry.getProductType().getId(),
                productEntry.getProductType().getName(),
                productEntry.getWeight(),
                productEntry.getManufacturer().getId(),
                productEntry.getManufacturer().getName(),
                productEntry.getIngredients(),
                productEntry.getProducts()
        );
    }

    public static List<ProductEntryDTO> from(List<ProductEntry> productEntries) {
        return productEntries.stream().map(ProductEntryDTO::from).collect(java.util.stream.Collectors.toList());
    }

    public static ProductEntryDTO fromWithNoLists(ProductEntry productEntry) {
        return new ProductEntryDTO(
                productEntry.getId(),
                productEntry.getName(),
                productEntry.getProductType().getId(),
                productEntry.getProductType().getName(),
                productEntry.getWeight(),
                productEntry.getManufacturer().getId(),
                productEntry.getManufacturer().getName(),
                productEntry.getIngredients(),
                null
        );
    }

    public static List<ProductEntryDTO> fromWithNoLists(List<ProductEntry> productEntries) {
        return productEntries.stream().map(ProductEntryDTO::fromWithNoLists).collect(java.util.stream.Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
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

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
