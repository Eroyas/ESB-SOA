package fr.unice.polytech.esb.flows.data;

import org.json.JSONObject;

public class Booking {
    private HotelReservation hotelReservation;
    // Un objet pour la réservation de vol
    // Un objet pour la réservation de voiture


    public HotelReservation getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservation hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject().put("type", "submit");
        JSONObject booking = new JSONObject().put("travelId", 100);
        JSONObject customer = new JSONObject()
                .put("customerId", 9)
                .put("firstName", "Mathias")
                .put("lastName", "Eroglu")
                .put("email", "teamswag@swag.al");

        JSONObject hotel = new JSONObject().put("hotel", hotelReservation.toJSONObject());

        booking.put("customer", customer);
        jsonObject.put("booking", booking);
        jsonObject.put("hotel", hotel);

        return jsonObject;
    }
}
