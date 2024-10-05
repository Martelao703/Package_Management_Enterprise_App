package pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "OrderCheckpoint")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderCheckpoints",
                query = "SELECT oc FROM OrderCheckpoint oc ORDER BY oc.id" // JPQL
        )
})
public class OrderCheckpoint implements Serializable {
    @Version
    private int version;
    @Id
    private String id;
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
    @NotNull
    @ManyToOne()
    @JoinColumn(name = "package_id")
    private Package package_;
    @NotNull
    @ManyToOne()
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;
    @NotNull
    private String location;
    @NotNull
    private LocalDate datetime;
    @NotNull
    private Float minTemperature; //in ºC
    @NotNull
    private Float maxTemperature; //in ºC
    @NotNull
    private Float minHumidity; //between 0-1
    @NotNull
    private Float maxHumidity; //between 0-1
    @NotNull
    private Float minAtmPressure; //1010 average, <1000 low, >1030 high
    @NotNull
    private Float maxAtmPressure; //1010 average, <1000 low, >1030 hig
    @NotNull
    private Float maxAcceleration; //>20 high
    @NotNull
    private boolean wasPackageOpened;

    public OrderCheckpoint() {
    }

    public OrderCheckpoint(Order order, Package package_, Carrier carrier, String location, LocalDate datetime, Float minTemperature, Float maxTemperature, Float minHumidity, Float maxHumidity, Float minAtmPressure, Float maxAtmPressure, Float maxAcceleration, boolean wasPackageOpened) {
        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.package_ = package_;
        this.carrier = carrier;
        this.location = location;
        this.datetime = datetime;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.minAtmPressure = minAtmPressure;
        this.maxAtmPressure = maxAtmPressure;
        this.maxAcceleration = maxAcceleration;
        this.wasPackageOpened = wasPackageOpened;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Package getPackage_() {
        return package_;
    }

    public void setPackage_(Package package_) {
        this.package_ = package_;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate datetime) {
        this.datetime = datetime;
    }

    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Float getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(Float minHumidity) {
        this.minHumidity = minHumidity;
    }

    public Float getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Float maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Float getMinAtmPressure() {
        return minAtmPressure;
    }

    public void setMinAtmPressure(Float minAtmPressure) {
        this.minAtmPressure = minAtmPressure;
    }

    public Float getMaxAtmPressure() {
        return maxAtmPressure;
    }

    public void setMaxAtmPressure(Float maxAtmPressure) {
        this.maxAtmPressure = maxAtmPressure;
    }

    public Float getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(Float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public boolean isWasPackageOpened() {
        return wasPackageOpened;
    }

    public void setWasPackageOpened(boolean wasPackageOpened) {
        this.wasPackageOpened = wasPackageOpened;
    }
}
