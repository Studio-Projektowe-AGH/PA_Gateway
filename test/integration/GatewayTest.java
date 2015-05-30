package integration;

import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

/**
 * Created by Marek on 2015-05-30.
 * Integration tests
 */
public class GatewayTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }

    @Test
    public void signup() throws Exception {
        running(fakeApplication(), () -> {
            FakeRequest fakeRequest = fakeRequest(POST, "/auth/signup").withJsonBody(
                    Json.parse("{\"email\": \"marek@skwarek\", \"password\": \"marekskwarek\", \"role\": \"business\"}")
            );
            Result route = route(fakeRequest);
            assertEquals("User already exists.", contentAsString(route));
        });
    }

    @Test
    public void signin() throws Exception {
        running(fakeApplication(), () -> {
            FakeRequest fakeRequest = fakeRequest(POST, "/auth/signin/credentials").withJsonBody(
                    Json.parse("{\"email\": \"marek@skwarek\", \"password\": \"marekskwarek\"}")
            );
            Result route = route(fakeRequest);
            assertTrue(contentType(route).contains("application/json") &&
                    contentAsString(route).contains("access_token"));

        });
    }

    @Test
    public void testToken() throws Exception {
        running(fakeApplication(), () -> {
            FakeRequest fakeRequest = fakeRequest(GET, "/test/token/random");
            Result route = route(fakeRequest);
            assertTrue(contentType(route).contains("application/json") &&
                    contentAsString(route).contains("access_token"));

        });
    }

    @Test
    public void allBusiness() throws Exception {
        running(fakeApplication(), () -> {
            FakeRequest fakeRequest = fakeRequest(GET, "/profiles/business/all");
            Result route = route(fakeRequest);
            assertTrue(contentType(route).contains("application/json") &&
                    contentAsString(route).contains("clubsIds"));
        });
    }
}
