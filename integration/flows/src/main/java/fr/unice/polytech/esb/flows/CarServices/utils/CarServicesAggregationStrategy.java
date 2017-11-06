package fr.unice.polytech.esb.flows.CarServices.utils;

import org.apache.camel.Exchange;

import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.List;

/**
 * Created by Eroyas on 30/10/17.
 */
public class CarServicesAggregationStrategy implements AggregationStrategy{

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange != null) {
            newExchange.getIn().getBody(List.class)
                    .addAll(oldExchange.getIn().getBody(List.class));
        }

        return newExchange;
    }

}

// https://github.com/apache/camel/blob/master/camel-core/src/main/docs/eips/aggregate-eip.adoc