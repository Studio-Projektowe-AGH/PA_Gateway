package services.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import models.LoginCredentials;
import net.minidev.json.JSONObject;
import org.bson.types.ObjectId;
import play.Play;
import play.libs.Json;
import services.db.DBLoginCredentialsService;

import java.text.ParseException;

/**
 * Created by Kris on 2015-05-15.
 */
public class AuthorizationService {

    static DBLoginCredentialsService dbLoginCredentialsService = DBLoginCredentialsService.getDbLoginCredentialsService();

    public static JsonNode getUserIds(String auth_token) throws ParseException, JOSEException, TokenPayloadException {
        String secret = Play.application().configuration().getString("jwt.secret");
        JWSObject jwsObject = JWSObject.parse(auth_token);
        JWSVerifier verifier = new MACVerifier(secret.getBytes());
        jwsObject.verify(verifier);

        JSONObject jsonObject = jwsObject.getPayload().toJSONObject();
        String userId = (String) jsonObject.get("userId");
        String userRole = (String) jsonObject.get("userRole");

        ObjectId userObjectId;
        try {
             userObjectId = new ObjectId(userId);
        } catch (Exception e) {
            throw new TokenPayloadException("Wrong format of userId. Contact LoginService developer to fix it");
        }
        LoginCredentials userCredentials = dbLoginCredentialsService.get(userObjectId);

        if (userCredentials == null) {
            throw new TokenPayloadException("There is no user with such id: " + userId);
        } else if (!userCredentials.getRole().toString().equals(userRole)) {
            throw new TokenPayloadException("There is user with this id: " + userId
                    + " but he has different role from that written in token" +
                    "contact LoginSerwice developer to fix it. " +
                    "Role in token: " + userRole +" role in db: " + userCredentials.getRole());
        }

        return Json.parse(jsonObject.toJSONString());
    }
}
