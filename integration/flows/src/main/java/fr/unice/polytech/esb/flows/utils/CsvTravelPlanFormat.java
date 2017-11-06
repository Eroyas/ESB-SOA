package fr.unice.polytech.esb.flows.utils;

import org.apache.camel.model.dataformat.CsvDataFormat;

public class CsvTravelPlanFormat {
    public static CsvDataFormat buildFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(";");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);
        return format;
    }
}
