package controllers;

import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Marek on 2015-05-21.
 */
public class EventsDispatcher extends Controller {
    private static final String EVENTS_URL = "http://thawing-sands-1576.herokuapp.com/";

    public static Result dispatch(String action) {
        WSResponse wsResponse = JsonDispatcher.dispatchPostRequest(EVENTS_URL + "events/" + action);
//        return ok("asd");

        if (wsResponse.getStatus() != OK) {
            return ok(wsResponse.getStatus() + " received, but it is ok");
        }


        return ok(new String(wsResponse.asByteArray()));
    }
}