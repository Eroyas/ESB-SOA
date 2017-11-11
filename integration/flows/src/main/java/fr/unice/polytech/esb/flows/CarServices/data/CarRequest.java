package fr.unice.polytech.esb.flows.CarServices.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Eroyas on 30/10/17.
 */
public class CarRequest implements Serializable {

    @JsonProperty private String cityDep;
    @JsonProperty private String cityArr;
    @JsonProperty private String dateDep;
    @JsonProperty private String duration;

    public CarRequest() {
        super();
    }

    public CarRequest(String cityDep, String cityArr, String dateDep, String duration) {
        this.cityDep = cityDep;
        this.cityArr = cityArr;
        this.dateDep = dateDep;
        this.duration = duration;
    }

    public String getCityDep() {
        return cityDep;
    }

    public void setCityDep(String cityDep) {
        this.cityDep = cityDep;
    }

    public String getCityArr() {
        return cityArr;
    }

    public void setCityArr(String cityArr) {
        this.cityArr = cityArr;
    }

    public String getDateDep() {
        return dateDep;
    }

    public void setDateDep(String dateDep) {
        this.dateDep = dateDep;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
