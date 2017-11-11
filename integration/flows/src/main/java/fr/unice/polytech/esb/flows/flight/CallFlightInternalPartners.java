package fr.unice.polytech.esb.flows.flight;

import fr.unice.polytech.esb.flows.data.TravelPlan;
import fr.unice.polytech.esb.flows.flight.data.FlightInformation;
import fr.unice.polytech.esb.flows.flight.data.FlightRequest;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;


/**
 * Created by GNINGK on 16/10/17.
 */
public class CallFlightInternalPartners extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        /*************************************************************************
         ** Flight reservation (a Document service implemented using REST/JSON) **
         *************************************************************************/
        onException(ExchangeTimedOutException.class)
                //TODO: Check that all exception are covered
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));
        onException(ConnectException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                .log("Cannot connect to flight external service")
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        from(USE_INTERNAL_FLIGHT_RESERVATION)
                .routeId("flight-internal-reservation-call")
                .routeDescription("Retrieve flight available depending of user criteria from internal service")


                .log("Creating retrieval request for flights using internal flights service")

                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/soap+xml"))
                .process(exchange -> exchange.getIn()
                   .setBody(adaptRequest(exchange.getIn().getBody(TravelPlan.class))))

                .inOut(CALL_INTERNAL_FLIGHT_RESERVATION)
                .process(decodeResponse);
    }


    private static String adaptRequest(TravelPlan flightRequest){
        // TODO: Get rid of "hard coded" parameter, improve demo
        String departureTime = flightRequest.getDateDepart();
        String originCountry = "France"; //flightRequest.getPaysDepart();
        String destinationCountry = "Norway"; //flightRequest.getPaysArrive();
        String user = "anonymous";

        return String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:flig=\"http://informatique.polytech.unice.fr/soa1/team/3/flightres/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <flig:listPossibleReservation>\n" +
                        "         <itineraryInfo>\n" +
                        "            <departureTime>%s</departureTime>\n" +
                        "            <destinationCountry>%s</destinationCountry>\n" +
                        "            <id>%s</id>\n" +
                        "            <originCountry>%s</originCountry>\n" +
                        "         </itineraryInfo>\n" +
                        "      </flig:listPossibleReservation>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>",
                departureTime, destinationCountry, user, originCountry);
    }

    private static FlightInformation xmlToFlightInfo(Node xmlFlightAttributes){
        NodeList xmlFlightsNodes = xmlFlightAttributes.getChildNodes();

        Double price = null;
        String origin = "";
        String destination = "";
        String date = "";

        for (int j = 0; j < xmlFlightsNodes.getLength(); j++) {
            Element xmlNode = (Element) xmlFlightsNodes.item(j);

            switch (xmlNode.getTagName()) {
                case "date":
                    date = xmlNode.getTextContent();
                    break;
                case "endingAirport":
                    destination = xmlNode.getTextContent();
                    break;
                case "price":
                    price = Double.parseDouble(xmlNode.getTextContent());
                    break;
                case "startingAirport":
                    origin = xmlNode.getTextContent();
                    break;
            }
        }
        FlightInformation flightInformation = new FlightInformation(origin, destination, date, price, "UNKNOWN");

        return flightInformation;
    }

    private static Processor decodeResponse = exchange -> {
        List<FlightInformation> flightsInformation = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;


        try{
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(exchange.getIn().getBody(String.class))));
            NodeList xmlFlights = document.getElementsByTagName("booking_info");

            for (int i = 0; i < xmlFlights.getLength(); i++) {
                flightsInformation.add(xmlToFlightInfo(xmlFlights.item(i)));
            }

        } catch (Exception e){

        }

        exchange.getIn().setBody(flightsInformation);
    };
}