package pt.ipleiria.estg.dei.ei.dae.projeto_dae.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.OrderCheckpoint;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class OrderCheckpointDTO implements Serializable {
    @Id
    private String id;
    private String orderID;
    private String packageID;
    private String carrierID;
    private String location;
    private LocalDate datetime;
    private Float minTemperature;
    private Float maxTemperature;
    private Float minHumidity;
    private Float maxHumidity;
    private Float minAtmPressure;
    private Float maxAtmPressure;
    private Float maxAcceleration;
    private boolean wasPackageOpened;

    public OrderCheckpointDTO() {
    }

<<<<<<< HEAD
    public OrderCheckpointDTO(String orderID, String packageID, String carrierID, String location, LocalDate datetime, Float minTemperature, Float maxTemperature, Float minHumidity, Float maxHumidity, Float minAtmPressure, Float maxAtmPressure, Float maxAcceleration, boolean wasPackageOpened) {
=======
    public OrderCheckpointDTO(String id, String orderID, String packageID, String carrierID, String location, Date datetime, Float minTemperature, Float maxTemperature, Float minHumidity, Float maxHumidity, Float minAtmPressure, Float maxAtmPressure, Float maxAcceleration, boolean wasPackageOpened) {
        this.id = id;
>>>>>>> 561ca037c92a0b389e567e355d1f93e0b44a7a69
        this.orderID = orderID;
        this.packageID = packageID;
        this.carrierID = carrierID;
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

    public static OrderCheckpointDTO from(OrderCheckpoint orderCheckpoint) {
        return new OrderCheckpointDTO(
            orderCheckpoint.getId(),
            orderCheckpoint.getOrder().getId(),
            orderCheckpoint.getPackage_().getId(),
            orderCheckpoint.getCarrier().getId(),
            orderCheckpoint.getLocation(),
            orderCheckpoint.getDatetime(),
            orderCheckpoint.getMinTemperature(),
            orderCheckpoint.getMaxTemperature(),
            orderCheckpoint.getMinHumidity(),
            orderCheckpoint.getMaxHumidity(),
            orderCheckpoint.getMinAtmPressure(),
            orderCheckpoint.getMaxAtmPressure(),
            orderCheckpoint.getMaxAcceleration(),
            orderCheckpoint.isWasPackageOpened()
        );
    }

    public static List<OrderCheckpointDTO> from(List<OrderCheckpoint> orderCheckpoints) {
        return orderCheckpoints.stream().map(OrderCheckpointDTO::from).collect(java.util.stream.Collectors.toList());
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getCarrierID() {
        return carrierID;
    }

    public void setCarrierID(String carrierID) {
        this.carrierID = carrierID;
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
