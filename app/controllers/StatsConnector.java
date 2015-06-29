package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.TokenAuthenticator;

/**
 * Created by Marek on 2015-06-29.
 *
 */
public class StatsConnector extends Controller {

    private static String serviceUrl = "https://pa-recommender.herokuapp.com/";

    @Security.Authenticated(TokenAuthenticator.class)
    private static Result post(String p) {
        final String username = request().username();
        if (username == null) {
            return unauthorized();
        }
        JsonNode user = Json.parse(username);

        String queryUrl = serviceUrl + "business_stats/" + p + "/" + user.get("userId").asText();

        return JsonDispatcher.dispatchPostRequest(queryUrl);
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result postRatio() {
        return post("ratio");
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result postValue() {
        return post("value");
    }
}
