package fr.unice.polytech.esb.flows.data;

import java.io.Serializable;

public class TravelPlan implements Serializable {
    private String paysDepart;
    private String paysArrive;
    private int durationInDay;

    // private String DateDepart; Je laisse comme ça pour l'instant. vu que j'intèqre uniquement mon
    // private String DateArrive; service pour la démo. On utilisera une lib comme Joda pour gérer les dates proprement


    public String getPaysDepart() {
        return paysDepart;
    }

    public void setPaysDepart(String paysDepart) {
        this.paysDepart = paysDepart;
    }

    public String getPaysArrive() {
        return paysArrive;
    }

    public void setPaysArrive(String paysArrive) {
        this.paysArrive = paysArrive;
    }

    public int getDurationInDay() {
        return durationInDay;
    }

    public void setDurationInDay(int durationInDay) {
        this.durationInDay = durationInDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelPlan that = (TravelPlan) o;

        if (durationInDay != that.durationInDay) return false;
        if (!paysDepart.equals(that.paysDepart)) return false;
        return paysArrive.equals(that.paysArrive);
    }

    @Override
    public int hashCode() {
        int result = paysDepart.hashCode();
        result = 31 * result + paysArrive.hashCode();
        result = 31 * result + durationInDay;
        return result;
    }
}
