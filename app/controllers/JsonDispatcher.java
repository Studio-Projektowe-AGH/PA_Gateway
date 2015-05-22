package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 */
public class JsonDispatcher extends Controller {
    private static final long TIMEOUT = 60*5000;

    public static WSResponse dispatchPostRequest(String path) {
        JsonNode jsonNode = request().body().asJson();
//        return null;

        WSResponse wsResponse = WS.url(path)
                .post(jsonNode)
                .get(TIMEOUT);

        return wsResponse;
    }

    public static WSResponse dispatchGetRequest(String path) {
        JsonNode jsonNode = request().body().asJson();

        WSResponse wsResponse = WS.url(path)
                .get()
                .get(TIMEOUT);

        return wsResponse;
    }
}
