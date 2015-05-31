package controllers;

import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Optional;
import java.util.stream.Collector;

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
        Optional<String> headers = request().headers().keySet().parallelStream().reduce((s, s2) -> s.concat(", " + s2));
        response().setHeader("Access-Control-Allow-Methods", "POST");
        response().setHeader("Access-Control-Allow-Headers", headers.get());
        return ok();
    }
}