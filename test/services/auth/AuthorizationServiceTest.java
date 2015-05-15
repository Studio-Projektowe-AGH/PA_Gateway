package services.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.JOSEException;
import org.junit.BeforeClass;
import org.junit.Test;
import play.mvc.Result;
import play.test.FakeRequest;

import java.text.ParseException;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static services.auth.AuthorizationService.getUserIds;

public class AuthorizationServiceTest {

    static play.test.FakeApplication application;

    @BeforeClass
    public static void setUpClass() {
        application = fakeApplication();
    }

    @Test
    public void testGetUserId() throws Exception {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTQ5MTVkMDUyNDIyOWY4Y2IwNDc1ZTQiLCJ1c2VyUm9sZSI6ImluZGl2aWR1YWwifQ.eDHKz_XsePl7wmqiargSJF7csNGN22xwF84WZvdh1O4";
                String correctId = "554915d0524229f8cb0475e4";

                try {
                    JsonNode receivedId = getUserIds(token);
                    assertEquals("Retreiving id from correct token ",
                            correctId, receivedId.get("userId").asText());

                } catch (ParseException e) {
                    e.printStackTrace();
                    assertTrue("Error whist parsing token", false);
                } catch (JOSEException e) {
                    e.printStackTrace();
                    assertTrue("Error whist verifying token", false);
                } catch (TokenPayloadException e) {
                    assertFalse(e.getMessage(), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void authorizationWithCorrectTokenTest(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String token = "eyJjdHkiOiJ0ZXh0XC9wbGFpbiIsImFsZyI6IkhTMjU2In0.eyJ1c2VySWQiOiI1NTRmYzg1OWU0YjBkYjFhZTc1MGI0M2YiLCJ1c2VyUm9sZSI6ImJ1c2luZXNzIn0.Xo3xCNWA38T-mVGaRISzRdydpzlo8ApvnO4FDwmMuMI";

                FakeRequest request = fakeRequest("GET", "/authTest").withHeader("Authorization", "Bearer " + token);
                Result result = route(request);

                assertEquals("When correct token in get authorized request: "+ contentAsString(result),
                        200, status(result));
            }
        });

    }

    @Test
    public void authorizationWithIncorrectTokenTest(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                String token = "this is fake token";
                FakeRequest request = fakeRequest("GET", "/authTest").withHeader("Authorization", "Bearer " +token);
                Result result = route(request);

                assertEquals("When incorrect token in get authorized request: "+ contentAsString(result),
                        401, status(result));
            }
        });

    }
}