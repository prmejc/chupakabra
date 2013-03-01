package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created with IntelliJ IDEA.
 * Worker: prmejc
 * Date: 11.2.2013
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context) {
        return context.session().get("userName");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.UserController.login());
    }
}
