package fr.unice.polytech.esb.flows.CarServices.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eroyas on 30/10/17.
 */
public class CarRequest implements Serializable {

    @JsonProperty private String CityDep;
    @JsonProperty private String CityArr;
    @JsonProperty private Date DateDep; // SimpleDateFormat.parse(String);
    @JsonProperty private Date DateArr;

    CarRequest() {
        super();
    }

    public CarRequest(String cityDep, String cityArr, Date dateDep, Date dateArr) {
        CityDep = cityDep;
        CityArr = cityArr;
        DateDep = dateDep;
        DateArr = dateArr;
    }

    public String getCityDep() {
        return CityDep;
    }

    public void setCityDep(String cityDep) {
        CityDep = cityDep;
    }

    public String getCityArr() {
        return CityArr;
    }

    public void setCityArr(String cityArr) {
        CityArr = cityArr;
    }

    public Date getDateDep() {
        return DateDep;
    }

    public void setDateDep(Date dateDep) {
        DateDep = dateDep;
    }

    public Date getDateArr() {
        return DateArr;
    }

    public void setDateArr(Date dateArr) {
        DateArr = dateArr;
    }

}
