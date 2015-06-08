package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.TokenAuthenticator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Kris on 2015-05-15.
 *
 * Remade by Marek
 */
public class ProfileServiceConnector extends Controller {
    private static final long TIMEOUT = 60000;
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

    interface MethodApplier {
        Promise<WSResponse> applyMethod(WSRequestHolder requestHolder);
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profile(MethodApplier method) {
        JsonNode user = Json.parse(request().username());

        String queryUrl = serviceUrl + user.get("userRole").asText() + "/" + user.get("userId").asText();

        WSRequestHolder url = WS.url(queryUrl);
        request().headers().forEach((key, values) -> Arrays.asList(values).forEach(value -> url.setHeader(key, value)));
        Promise<WSResponse> promise = method.applyMethod(url);

        Promise<Status> nodePromise = promise.map(new Function<WSResponse, Status>() {

            @Override
            public Status apply(WSResponse wsResponse) throws Throwable {
                if (wsResponse.getStatus() == NOT_FOUND) {
                    UpdateConnector.updateG(user.get("userId").asText());

                    wsResponse = method.applyMethod(url).get(TIMEOUT);
                }

                if (wsResponse.getStatus() != OK) {
                    Map<String, String> ret = new LinkedHashMap<>();
                    ret.put("status", String.valueOf(wsResponse.getStatus()));
                    ret.put("statusText", wsResponse.getStatusText());
                    ret.put("request", queryUrl);
                    ret.put("message", new String(wsResponse.asByteArray()));

                    response().setHeader(CONTENT_TYPE, "application/json");
                    return status(wsResponse.getStatus(), Json.toJson(ret).toString());
                }

                wsResponse.getAllHeaders().forEach((key, values) -> values.forEach(value -> response().setHeader(key, value)));

                return ok(new String(wsResponse.asByteArray()));
            }
        });

        return nodePromise.get(TIMEOUT);
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profileGet() {
        return profile(WSRequestHolder::get);
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profilePost() {
        return profile(requestHolder -> requestHolder.post(request().body().asJson()));
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profileDelete() {
        return profile(WSRequestHolder::delete);
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result profilePut() {
        return profile(requestHolder -> requestHolder.put(request().body().asJson()));
    }
}
