import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.lang.reflect.Method;

/**
 * Created by Marek on 2015-05-24.
 * Settings applied to all requests
 */
public class Global extends GlobalSettings {

    private class ActionWrapper extends Action.Simple {
        ActionWrapper(Action action) {
            this.delegate = action;
        }

        @Override
        public F.Promise<Result> call(Http.Context context) throws Throwable {
            F.Promise<Result> result = this.delegate.call(context);
            Http.Response response = context.response();
            response.setHeader("Access-Control-Allow-Origin", "*");
            return result;
        }
    }

    @Override
    public Action onRequest(Http.Request request, Method method) {
        return new ActionWrapper(super.onRequest(request, method));
    }
}
