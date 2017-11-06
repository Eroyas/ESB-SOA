package fr.unice.polytech.esb.flows;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CallBookingService extends RouteBuilder{

    private static String HOST = "booking-management";
    private static String PORT = "8080";
    private static String SERVICE = "tta-booking";
    private static String ENDPOINT = "booking";


    @Override
    public void configure() throws Exception {
        from(BOOKING_Q)
                .routeId("book-a-travel-plan")
                .routeDescription("Call the booking service to submit a travel plan and await valdiation")
                .log("Calling booking service with hotel data: ${body.hotelName} and price: ${body.price}")
                .setProperty("hotelName",simple("${body.hotelName}"))
                .setProperty("hotelPrice",simple("${body.price}"))
                .inOut(BOOKING_SERVICE);

        from(BOOKING_SERVICE)
                .routeId("send-a-booking-request")
                .routeDescription("Send a request for booking at the booking service")
                .process(callBookingService)
                .end();
    }

    public static Processor callBookingService = (Exchange exchange) -> {

        String hotelName = exchange.getProperty("hotelName", String.class);
        int hotelPrice = exchange.getProperty("hotelPrice", Integer.class);

        Client client = ClientBuilder.newClient();


        JSONObject json = new JSONObject().put("type","submit");
        JSONObject booking = new JSONObject().put("travelId",100);
        JSONObject hotelReservation = new JSONObject()
                .put("name", hotelName)
                .put("room", 10)
                .put("price", hotelPrice);

        JSONObject customer = new JSONObject()
                .put("customerId", 9)
                .put("firstName", "Mathias")
                .put("lastName", "Eroglu")
                .put("email", "teamswag@swag.al");

        booking.put("hotel", hotelReservation);
        booking.put("customer", customer);
        json.put("booking", booking);

        System.out.println("Sending request : \n"+json.toString());

        Response response = client.target("http://" + HOST + ":" + PORT + "/" + SERVICE + "/" + ENDPOINT)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(json.toString()));


        client.close();

        String raw = response.readEntity(String.class);
        JSONObject result = new JSONObject(raw);
        System.out.println("Got response from booking :\n "+raw);
    };
}
