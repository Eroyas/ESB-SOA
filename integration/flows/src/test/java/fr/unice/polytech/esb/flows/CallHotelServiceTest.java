package fr.unice.polytech.esb.flows;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Before;
import org.junit.Test;
import stubs.hotelservice.HotelFinderService;

import static fr.unice.polytech.esb.flows.utils.Endpoints.HOTEL_RESERVATION_Q;



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
    }

}
