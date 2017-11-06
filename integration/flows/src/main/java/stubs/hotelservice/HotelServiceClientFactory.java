package stubs.hotelservice;

import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HotelServiceClientFactory {

    // Store in memory, the available stubs
    private static Map<String, HotelFinderService> clients = new HashMap<>();

    public static HotelFinderService getInstance(String host, String port) {
        HotelFinderService client = null;
        if (!clients.containsKey(host+":"+port)) {
            URL wsdlLocation = HotelServiceClientFactory.class.getResource("/WSDLs/ExternalHotelFinderService.wsdl");
            ExternalHotelFinderService factory = new ExternalHotelFinderService(wsdlLocation);
            client = factory.getExternalHotelFinderPort();
            String address = "http://" + host + ":" + port + "/hotels-service";
            ((BindingProvider) client).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
            clients.put(host + ":" + port, client);
        }
        return clients.get(host + ":" + port);
    }

    public static void deleteInstance(HotelFinderService client) {
        for (Map.Entry<String, HotelFinderService> currentClient : clients.entrySet()) {
            if (client == currentClient) {
                clients.remove(currentClient.getKey());
            }
        }
    }
}
