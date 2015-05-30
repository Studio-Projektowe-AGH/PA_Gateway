package controllers;

import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 *
 * Dispatches requests to authentication service
 */
public class AuthDispatcher extends Controller {
    private static final String AUTH_URL = "http://goparty-auth.herokuapp.com/";

    public static Result dispatchRequest(String path) {
        return JsonDispatcher.dispatchPostRequest(AUTH_URL + path);
//        WSResponse wsResponse = JsonDispatcher.dispatchPostRequest(AUTH_URL + path);
//
//        if (wsResponse.getStatus() == OK) {
//            return ok(wsResponse.asJson());
//        }
//
//        return internalServerError(new String(wsResponse.asByteArray()));
    }

    public static Result dispatchSocialRequest(String provider) {
        return dispatchRequest("auth/signin/" + provider);
    }

    public static Result testToken() {
        WSResponse wsResponse = JsonDispatcher.dispatchGetRequest(AUTH_URL + "test/token/random");
        if (wsResponse.getStatus() != OK) {
            return ok("Ege szege dreciokolo masajo osto kuto hojo todo buroki");
        }

        return ok(wsResponse.asJson());
    }
}
