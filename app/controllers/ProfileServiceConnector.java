package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import enums.Method;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
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
import static play.mvc.Results.*;


/**
 * Created by Kris on 2015-05-15.
 */
public class ProfileServiceConnector {
    private static final long TIMEOUT = 60000;
    static String serviceUrl = "https://goparty-profile.herokuapp.com/profiles/";

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result getProfile() {
        JsonNode userIds = Json.parse(request().username());
        String url = serviceUrl + userIds.get("userRole").asText() +"/" + userIds.get("userId").asText();
        System.out.println("Sciezka ktora pytamy: " +url);
        Promise<JsonNode> jsonPromise = WS.url(url).get().map(
                new Function<WSResponse, JsonNode>() {
                    public JsonNode apply(WSResponse response) {
                        if(response.getStatus() == 200){
                            return response.asJson();
                        }else {
                            System.out.println("Status: "+ response.getStatus());
                            return null;
                        }
                    }
                }
        );
        JsonNode profileInJson = jsonPromise.get(4000L);
        if(profileInJson != null){
            return ok(profileInJson);
        } else{
            return internalServerError("Something went wrong: good token, but cannot retrieve data from Profile Service " + url);
        }
    }

    public static Result postProfile() {
        return play.mvc.Results.TODO;
    }

    public static Result putProfile() {
        return play.mvc.Results.TODO;
    }

    public static Result deleteProfile() {
        return play.mvc.Results.TODO;
    }

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
    public static Result profile(Method method) {
        JsonNode user = Json.parse(request().username());

        String queryUrl = serviceUrl + user.get("userRole").asText() + "/" + user.get("userId").asText();

        WSRequestHolder requestHolder = WS.url(queryUrl);

        Promise<WSResponse> promise = null;
        switch (method) {
            case GET:    promise = requestHolder.get(); break;
            case POST:   return TODO;
            case DELETE: return TODO;
            case PUT:    return TODO;
        }

        JsonNode response = promise.map(WSResponse::asJson).get(TIMEOUT);

        return ok(response);
    }
}
