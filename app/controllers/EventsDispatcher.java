package controllers;

import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 * Dispatching to events service logic
 */
public class EventsDispatcher extends Controller {
    private static final String EVENTS_URL = "http://goparty-eventq.herokuapp.com/";

    public static Result dispatch(String action) {
        return JsonDispatcher.dispatchPostRequest(EVENTS_URL + "events/" + action);
    }

    public static Result options(String action) {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST");
        response().setHeader("Access-Control-Allow-Headers", "*");
        response().setHeader("Access-Control-Allow-Credentials", "true");
        return ok();
    }
}