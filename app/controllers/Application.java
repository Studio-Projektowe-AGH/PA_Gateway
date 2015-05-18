package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.auth.TokenAuthenticator;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    @Security.Authenticated(TokenAuthenticator.class)
    public static Result indexTest() {
        return ok(index.render());
    }

}
