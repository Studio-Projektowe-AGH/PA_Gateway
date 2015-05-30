package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 */
public class JsonDispatcher extends Controller {
    private static final long TIMEOUT = 60*5000;

    public static WSResponse dispatchPostRequest(String path) {
        JsonNode jsonNode = request().body().asJson();

        return WS.url(path)
                 .post(jsonNode)
                 .get(TIMEOUT);
    }

    public static WSResponse dispatchGetRequest(String path) {
        return WS.url(path)
                .get()
                .get(TIMEOUT);
    }
}
