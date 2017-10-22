package stubs.hotelservice;

import javax.xml.ws.BindingProvider;
import java.net.URL;

public class HotelServiceClientFactory {
    public static HotelFinderService newHotelFinderServiceClient(String host, String port) {
        URL wsdlLocation = HotelServiceClientFactory.class.getResource("/WSDLs/ExternalHotelFinderService.wsdl");
        ExternalHotelFinderService factory = new ExternalHotelFinderService(wsdlLocation);
        HotelFinderService client = factory.getExternalHotelFinderPort();
        String address = "http://" + host + ":" + port + "/hotels-service/ExternalHotelFinderService";
        ((BindingProvider) client).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return client;
    }
}
