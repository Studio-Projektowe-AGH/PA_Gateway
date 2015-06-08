package controllers;

import models.LoginCredentials;
import org.bson.types.ObjectId;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import services.db.DBLoginCredentialsService;

/**
 * Created by Marek on 2015-06-07.
 * Used to trigger profile update
 */
public class UpdateConnector extends Controller {
    private static final long TIMEOUT = 60000;

    static DBLoginCredentialsService profile = DBLoginCredentialsService.getDbLoginCredentialsService();

    public static Result updateFG(String role, String fb_token, String gp_token) {
        String service_url = "http://data-gatherer.herokuapp.com/update/";
        String queryUrl = service_url + role;

        WSRequestHolder url = WS.url(queryUrl);
        url.setContentType(HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED);
        F.Promise<WSResponse> promise = url.post("fbToken=" + fb_token + "&gpToken=" + gp_token);

        F.Promise<Status> statusPromise = promise.map(wsResponse -> status(wsResponse.getStatus(), wsResponse.asByteArray()));

        return statusPromise.get(TIMEOUT);
    }

    public static Result updateG(String gp_token) {
        ObjectId oid = new ObjectId(gp_token);
        LoginCredentials credentials = profile.get(oid);
        String accessToken = credentials.getSocialCredentials().get(0).getAccessToken();

        return updateFG(credentials.getRole().name(), accessToken, gp_token);
    }
}
