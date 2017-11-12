package fr.unice.polytech.esb.flows.aggregation;

import fr.unice.polytech.esb.flows.data.HotelReservation;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.List;

public class HotelAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange != null) {
            HotelReservation one = oldExchange.getProperty("hotelReservation", HotelReservation.class);
            System.out.println("old property price: "+one.getPrice());
            if (newExchange != null) {
                HotelReservation two = newExchange.getProperty("hotelReservation", HotelReservation.class);
                return one.getPrice() < two.getPrice() ? oldExchange : newExchange;
            }
            return oldExchange;
        }
        HotelReservation e = newExchange.getProperty("hotelReservation", HotelReservation.class);
        System.out.println("new property price : "+ e.getPrice());
        return newExchange;
    }
}
