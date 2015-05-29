package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.TokenAuthenticator;

import java.util.ArrayList;
import java.util.Arrays;

import static play.mvc.Controller.request;
import static play.mvc.Controller.response;
import static play.mvc.Http.Status.OK;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;


/**
 * Created by Kris on 2015-05-15.
 *
 * Remade by Marek
 */
public class ProfileServiceConnector {
    static String serviceUrl = "https://goparty-profile.herokuapp.com/profiles/";

    public static Result getLocalList() {
        ArrayList<String> ids = new ArrayList<>(Arrays.asList("pierwszy", "drugi", "treci"));

        WSResponse wsResponse = JsonDispatcher.dispatchGetRequest(serviceUrl + "business/all");
        if (wsResponse.getStatus() != OK) {
            return ok(Json.toJson(ids));
        }

        response().setHeader("Access-Control-Allow-Origin", "*");
        return ok(wsResponse.asJson());
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profileRedirect() {
        JsonNode user = Json.parse(request().username());

        String queryUrl = serviceUrl + user.get("userRole").asText() + "/" + user.get("userId").asText();
        return redirect(queryUrl);
    }

}
