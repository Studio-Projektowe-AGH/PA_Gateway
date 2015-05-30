package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Arrays;

/**
 * Created by Marek on 2015-05-21.
 * Util with template actions
 */
public class JsonDispatcher extends Controller {
    private static final long TIMEOUT = 60*5000;

    public static Status dispatchPostRequest(String path) {
        JsonNode jsonNode = request().body().asJson();
        WSRequestHolder url = WS.url(path);

        request().headers().forEach((key, values) -> Arrays.asList(values).forEach(value -> url.setHeader(key, value)));

        WSResponse wsResponse = url.post(jsonNode).get(TIMEOUT);
        wsResponse.getAllHeaders().forEach((key, values) -> values.forEach(value -> response().setHeader(key, value)));

        return ok(new String(wsResponse.asByteArray()));
    }

    public static WSResponse dispatchGetRequest(String path) {
        return WS.url(path)
                .get()
                .get(TIMEOUT);
    }
}
