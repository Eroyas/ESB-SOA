package fr.unice.polytech.esb.flows.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stubs.hotelservice.Hotel;
import stubs.hotelservice.HotelFinderService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Maxime
 */
public class HotelServiceProcessor implements Processor {

    private static final transient Logger LOG = LoggerFactory.getLogger(HotelServiceProcessor.class);

    HotelFinderService hotelFinderService;

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();
        String operationName = inMessage.getHeader("opeation_name", String.class);

        if ("recherche".equals(operationName)) {
            String destination = exchange.getProperty("destination",String.class);
            int duration = exchange.getProperty("duration", Integer.class);
            LOG.info("Calling recherche with argument destination : "+destination+" and duration : "+duration);
            List<Hotel> hotels = hotelFinderService.recherche(destination, duration, false);
            LOG.info("Got result from recherche : "+ hotels.size());
        }
    }

    public void setHotelFinderService(HotelFinderService hotelFinderService) {
        this.hotelFinderService = hotelFinderService;
    }
}
