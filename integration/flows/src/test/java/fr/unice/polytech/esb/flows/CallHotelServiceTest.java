package fr.unice.polytech.esb.flows;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import stubs.hotelservice.Hotel;
import stubs.hotelservice.HotelFinderService;
import stubs.hotelservice.HotelServiceClientFactory;

import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.HOTEL_RESERVATION_Q;
import static fr.unice.polytech.esb.flows.utils.Endpoints.HOTEL_SEARCH;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;



public class CallHotelServiceTest extends ActiveMQTest {

    private static HotelFinderService hotelFinderServiceClient;

    private static final String hotelReservationQMock = "mock://" + HOTEL_RESERVATION_Q;

    private String hotelRequest;

    @Override
    public String isMockEndpointsAndSkip() {
        return HOTEL_RESERVATION_Q;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        CallHotelService hotelRoute = new CallHotelService();
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.includeRoutes(hotelRoute);
            }
        };
    }

    @Before
    public void initMocks() {
        resetMocks();
    }

    @Test
    public void testExecutionContext() {
        assertNotNull(context.hasEndpoint(HOTEL_RESERVATION_Q));
        assertNotNull(context.hasEndpoint(hotelReservationQMock));
    }

    @Test
    public void testHotelService() throws Exception {
        mock(HOTEL_RESERVATION_Q).expectedMessageCount(1);
        mock(HOTEL_SEARCH).expectedMessageCount(1);
        String message = template.requestBody(HOTEL_RESERVATION_Q, "Paris", String.class);

    }

}
