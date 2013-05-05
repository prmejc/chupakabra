package models;

import org.jasypt.util.password.BasicPasswordEncryptor;
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
        BasicPasswordEncryptor bpe = new BasicPasswordEncryptor();
        this.userName = userName;
        this.password = bpe.encryptPassword(password);
        this.admin = admin;
        this.dateCreate = new Date();
        this.dateModify = new Date();
    }

    public static HomeUser authenticate(String userName, String password) {
        BasicPasswordEncryptor bpe = new BasicPasswordEncryptor();
        HomeUser hu = find.where().eq("userName", userName).findUnique();
        if(hu != null)
            if (bpe.checkPassword(password, hu.password))
                return hu;

        return null;
    }

    @Override
    public void save() {
        dateModify = new Date();
        super.save();
    }



    @Override
    public void update() {
        dateModify = new Date();
        super.update();
    }
}
