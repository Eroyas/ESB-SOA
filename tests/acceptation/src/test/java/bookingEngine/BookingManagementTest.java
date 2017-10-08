package bookingEngine;

import dockerized.DockerizedTest;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@Ignore
public class BookingManagementTest extends DockerizedTest{


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
        //TODO: vraiment dégueu, to be fixed
        JSONObject identities = new JSONObject("{\n" +
                "  \"array\": [\n" +
                "    {\n" +
                "      \"firstName\": \"Anica\",\n" +
                "      \"lastName\": \"Matthisson\",\n" +
                "      \"email\": \"amatthisson0@springer.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Bernie\",\n" +
                "      \"lastName\": \"Jepps\",\n" +
                "      \"email\": \"bjepps1@nhs.uk\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Harmonie\",\n" +
                "      \"lastName\": \"Barrows\",\n" +
                "      \"email\": \"hbarrows2@booking.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Verge\",\n" +
                "      \"lastName\": \"Purcell\",\n" +
                "      \"email\": \"vpurcell3@xing.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Sara\",\n" +
                "      \"lastName\": \"McBean\",\n" +
                "      \"email\": \"smcbean4@ucla.edu\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Koral\",\n" +
                "      \"lastName\": \"Gurley\",\n" +
                "      \"email\": \"kgurley5@over-blog.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Donelle\",\n" +
                "      \"lastName\": \"Napper\",\n" +
                "      \"email\": \"dnapper6@behance.net\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Granville\",\n" +
                "      \"lastName\": \"Toffoloni\",\n" +
                "      \"email\": \"gtoffoloni7@google.it\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Lela\",\n" +
                "      \"lastName\": \"Siemon\",\n" +
                "      \"email\": \"lsiemon8@wikispaces.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Reggis\",\n" +
                "      \"lastName\": \"Zorzi\",\n" +
                "      \"email\": \"rzorzi9@wired.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Caldwell\",\n" +
                "      \"lastName\": \"Dies\",\n" +
                "      \"email\": \"cdiesa@uol.com.br\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Selinda\",\n" +
                "      \"lastName\": \"Duddle\",\n" +
                "      \"email\": \"sduddleb@cisco.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Philbert\",\n" +
                "      \"lastName\": \"Everit\",\n" +
                "      \"email\": \"peveritc@t.co\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Bill\",\n" +
                "      \"lastName\": \"Sinkins\",\n" +
                "      \"email\": \"bsinkinsd@linkedin.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Kandace\",\n" +
                "      \"lastName\": \"Cuerdale\",\n" +
                "      \"email\": \"kcuerdalee@photobucket.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"firstName\": \"Anders\",\n" +
                "      \"lastName\": \"Foucard\",\n" +
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
