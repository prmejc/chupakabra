package controllers;

import models.HomeUser;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * Worker: prmejc
 * Date: 11.2.2013
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class UserController extends Controller {

    public static Result login() {
        return ok(
                login.render(form(Login.class), null)
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm, null));
        } else {
            session().clear();
            session("userName", loginForm.get().userName);
            flash("success", "Pozdravljen!");
            return redirect(
                    routes.Application.index()
            );
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "Uspešno si se odjavil");
        return redirect(
                routes.UserController.login()
        );
    }





    public static class Login {

        public String userName;
        public String password;

        public String validate() {
            if (HomeUser.authenticate(userName, password) == null) {
                return "Napačno uporabniško ime ali geslo";
            }
            return null;
        }

    }
}
