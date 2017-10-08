package bookingEngine;

import dockerized.DockerizedTest;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.MediaType;

public class BookingActionsTest extends DockerizedTest{


    private static final int[] ids = new int[]{(int) (Math.random()*10000), (int) (Math.random()*10000), (int) (Math.random()*10000)};
    //private static final JSONObject identities = new JSONObject(new File("./database/datasets/identities.json"));

    @Before
    @Test
    public void submitBookingTest() {
        JSONObject booking, answer;

        for(int i = 0; i < 3; i++){
            booking = createBooking(i);
            answer = Assets.call(booking);

            JSONAssert.assertEquals(booking.getJSONObject("booking"), answer.getJSONObject("booking"), false);
            Assert.assertTrue(answer.getJSONObject("booking").optString("status").equals("WAITING_APPROVAL"));
            Assert.assertTrue(answer.getBoolean("inserted"));
        }
    }

    private JSONObject generateIdentity()
    {
        JSONObject identities = new JSONObject("{\n" +
                "  \"array\": [\n" +
                "    {\n" +
                "      \"first_name\": \"Anica\",\n" +
                "      \"last_name\": \"Matthisson\",\n" +
                "      \"email\": \"amatthisson0@springer.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Bernie\",\n" +
                "      \"last_name\": \"Jepps\",\n" +
                "      \"email\": \"bjepps1@nhs.uk\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Harmonie\",\n" +
                "      \"last_name\": \"Barrows\",\n" +
                "      \"email\": \"hbarrows2@booking.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Verge\",\n" +
                "      \"last_name\": \"Purcell\",\n" +
                "      \"email\": \"vpurcell3@xing.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Sara\",\n" +
                "      \"last_name\": \"McBean\",\n" +
                "      \"email\": \"smcbean4@ucla.edu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Koral\",\n" +
                "      \"last_name\": \"Gurley\",\n" +
                "      \"email\": \"kgurley5@over-blog.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Donelle\",\n" +
                "      \"last_name\": \"Napper\",\n" +
                "      \"email\": \"dnapper6@behance.net\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Granville\",\n" +
                "      \"last_name\": \"Toffoloni\",\n" +
                "      \"email\": \"gtoffoloni7@google.it\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Lela\",\n" +
                "      \"last_name\": \"Siemon\",\n" +
                "      \"email\": \"lsiemon8@wikispaces.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Reggis\",\n" +
                "      \"last_name\": \"Zorzi\",\n" +
                "      \"email\": \"rzorzi9@wired.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Caldwell\",\n" +
                "      \"last_name\": \"Dies\",\n" +
                "      \"email\": \"cdiesa@uol.com.br\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Selinda\",\n" +
                "      \"last_name\": \"Duddle\",\n" +
                "      \"email\": \"sduddleb@cisco.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Philbert\",\n" +
                "      \"last_name\": \"Everit\",\n" +
                "      \"email\": \"peveritc@t.co\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Bill\",\n" +
                "      \"last_name\": \"Sinkins\",\n" +
                "      \"email\": \"bsinkinsd@linkedin.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Kandace\",\n" +
                "      \"last_name\": \"Cuerdale\",\n" +
                "      \"email\": \"kcuerdalee@photobucket.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"first_name\": \"Anders\",\n" +
                "      \"last_name\": \"Foucard\",\n" +
                "      \"email\": \"afoucardf@joomla.org\"\n" +
                "    }]}");
        int nb = (int) (Math.random()*10);
        return identities.getJSONArray("array").getJSONObject(nb);
    }

    @Test
    public void validateBookingTest() {
        //Validating the booking we just posted
        JSONObject request = new JSONObject()
                .put("type", "validate")
                .put("id", ids[0]);

        JSONObject answer = Assets.call(request);

        Assert.assertTrue(answer.getInt("id") == ids[0]);
        Assert.assertTrue(answer.getBoolean("approved"));
    }

    @Test
    public void rejectBookingTest() {
        //Validating the booking we just posted
        JSONObject request = new JSONObject()
                .put("type", "reject")
                .put("id", ids[1]);

        JSONObject answer = Assets.call(request);

        Assert.assertTrue(answer.getInt("id") == ids[1]);
        Assert.assertTrue(answer.getBoolean("rejected"));
    }

    @Test
    public void retrieveBookingTest() {
        JSONObject request = new JSONObject()
                .put("type", "retrieve")
                .put("id", ids[2]);

        JSONObject answer = Assets.call(request);

        Assert.assertTrue(ids[2] == answer.getInt("id"));
        Assert.assertTrue(answer.getBoolean("retrieved"));
        Assert.assertTrue("WAITING_APPROVAL".equals(answer.getString("status")));
    }

    /* ASSETS */

    private JSONObject createBooking(int i){
        JSONObject flight = new JSONObject()
                .put("number", (int) (Math.random() * 10000))
                .put("airline", "Air Whatever");

        return new JSONObject()
                .put("type", "submit")
                .put("booking", new JSONObject()
                        .put("id", ids[i])
                        .put("identity", generateIdentity())
                        .put("flight", flight));
    }
}
