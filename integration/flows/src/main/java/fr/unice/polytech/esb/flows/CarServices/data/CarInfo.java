package fr.unice.polytech.esb.flows.CarServices.data;

import java.io.Serializable;

/**
 * Created by Eroyas on 14/10/17.
 */
public class CarInfo implements Serializable {

    private String brand;
    private String model;
    private String place;
    private int rentPricePerDay;
    private int availability;

    public CarInfo() {}

    public CarInfo(String brand, String model, String place, int rentPricePerDay, int availability) {
        this.brand = brand;
        this.model = model;
        this.place = place;
        this.rentPricePerDay = rentPricePerDay;
        this.availability = availability;
    }

    public CarInfo(CarInfo car) {
        this.brand = car.brand;
        this.model = car.model;
        this.place = car.place;
        this.rentPricePerDay = car.rentPricePerDay;
        this.availability = car.availability;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(int rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarInfo carInfo = (CarInfo) o;

        if (getRentPricePerDay() != carInfo.getRentPricePerDay()) return false;
        if (availability != carInfo.availability) return false;
        if (getBrand() != null ? !getBrand().equals(carInfo.getBrand()) : carInfo.getBrand() != null) return false;
        if (getModel() != null ? !getModel().equals(carInfo.getModel()) : carInfo.getModel() != null) return false;
        return getPlace().equals(carInfo.getPlace());
    }

    @Override
    public int hashCode() {
        int result = getBrand() != null ? getBrand().hashCode() : 0;
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + getRentPricePerDay();
        result = 31 * result + availability;
        return result;
    }

}
