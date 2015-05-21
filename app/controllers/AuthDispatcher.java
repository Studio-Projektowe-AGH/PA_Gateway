package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 *
 * Dispatches requests to authentication service
 */
public class AuthDispatcher extends Controller {
    private static final long TIMEOUT = 5000;

    public static Result dispatchRequest(String path) {
        JsonNode jsonNode = request().body().asJson();

        WSResponse wsResponse = WS.url("http://partyadvisor.herokuapp.com/" + path)
                .post(jsonNode)
                .get(TIMEOUT);

        return ok(new String(wsResponse.asByteArray()));
    }

    public static Result dispatchSocialRequest(String provider) {
        return dispatchRequest(provider);
    }
}
