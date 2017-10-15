package fr.unice.polytech.esb.flows.utils;

public class HotelSearcHelper {
    public String buildRequest(String destination) {
        StringBuilder builder = new StringBuilder();

        builder.append("<cook:recherche xmlns:cook=\"http://cookbook.soa1.polytech.unice.fr/\">\n");
        builder.append("  <lieu>"+destination+"</lieu>\n");
        builder.append("  <dure>" + 1 + "</dure>\n");
        builder.append("  <sortedAsc>" + false + "</sortedAsc>\n");
        builder.append("</cook:recherche>");
        return builder.toString();
    }
}