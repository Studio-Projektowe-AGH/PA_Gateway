package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.TokenAuthenticator;

/**
 * Created by Marek on 2015-06-28.
 * 
 */
public class Recommendation extends Controller {

    private static String serviceUrl = "https://pa-recommender.herokuapp.com/";

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result clubsGet(int count, double x, double y) {
        JsonNode user = Json.parse(request().username());
        String queryUrl = serviceUrl + "recommend/clubs/" + user.get("userId").asText();

        Logger.debug(queryUrl + "?x=" + x + "&y=" + y + "&count=" + count);
        final WSRequestHolder wsRequestHolder = WS.url(queryUrl + "?x=1" + "&y=2" + /*(int)y*/ /*+*/ "&count=" + count);
//                .setQueryParameter("x", String.valueOf(x))
//                .setQueryParameter("y", y);
//                .setQueryParameter("count", String.valueOf(count));

        final F.Promise<WSResponse> wsResponsePromise = wsRequestHolder
                .get();

        final JsonNode map = wsResponsePromise
                .map(WSResponse::asJson)
                .get(60000);

        return ok(map);
    }
}
