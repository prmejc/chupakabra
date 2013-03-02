package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * HomeUser: prmejc
 * Date: 1.3.2013
 * Time: 16:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class HomeUser extends Model{
    @Id
    public String userName;
    public String password;
    public boolean admin;
    public Date dateCreate;
    public Date dateModify;

    public static Model.Finder<String, HomeUser> find = new Model.Finder<String, HomeUser>(String.class, HomeUser.class);


    public HomeUser(String userName, String password, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.dateCreate = new Date();
        this.dateModify = new Date();
    }

    public static HomeUser authenticate(String userName, String password) {
        System.out.println(userName +"  " + password);
        return find.where().eq("userName", userName)
                .eq("password", password).findUnique();
    }

    @Override
    public void save() {
        dateCreate = new Date();
        dateModify = new Date();
        super.save();
    }



    @Override
    public void update() {
        dateModify = new Date();
        super.update();
    }
}
