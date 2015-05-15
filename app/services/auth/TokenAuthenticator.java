package services.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.JOSEException;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.text.ParseException;

/**
 * Created by Kris on 2015-05-15.
 */
public class TokenAuthenticator extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context context) {
        String auth_token = context.request().getHeader("Authorization").split(" ")[1];
        if(auth_token == null)
            return null;

        JsonNode userIds = null;
        try {
            userIds = AuthorizationService.getUserIds(auth_token);
        } catch (ParseException e) {

            return null;
        } catch (JOSEException e) {
            return null;
        } catch (TokenPayloadException e) {
            return null;
        }
        return userIds.toString();
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return super.onUnauthorized(context);
    }
}
