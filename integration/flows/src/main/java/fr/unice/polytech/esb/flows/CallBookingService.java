package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.Booking;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
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
                //.log("Calling booking service with hotel data: ${body.hotelName} and price: ${body.price}")
                //.setProperty("hotelName",simple("${body.hotelName}"))
                //.setProperty("hotelPrice",simple("${body.price}"))
                .inOut(BOOKING_SERVICE);

        from(BOOKING_SERVICE)
                .routeId("send-a-booking-request")
                .routeDescription("Send a request for booking at the booking service")
                .log("Booking service body: ${body}")
                .process(callBookingService)
                .end();
    }

    public static Processor callBookingService = (Exchange exchange) -> {

        Client client = ClientBuilder.newClient();

        Booking theBooking = exchange.getIn().getBody(Booking.class);

        System.out.println("Sending request : \n"+theBooking.toJSONObject().toString());

        Response response = client.target("http://" + HOST + ":" + PORT + "/" + SERVICE + "/" + ENDPOINT)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(theBooking.toJSONObject().toString()));


        client.close();

        String raw = response.readEntity(String.class);
        JSONObject result = new JSONObject(raw);
        System.out.println("Got response from booking :\n "+raw);
    };
}
