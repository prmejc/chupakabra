package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.3.2013
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Pin extends Model {

    @Id
    public Long pinId;
    public String type;
    public int status;
    public Date dateCreate;
    public Date dateModify;
    @Version
    public int version;

    public static Model.Finder<Long, Pin> find = new Finder<Long, Pin>(Long.class, Pin.class);


    public Pin(Long pinId, String type, int status) {
        this.pinId = pinId;
        this.type = type;
        this.status = status;
        this.dateCreate = new Date();
        this.dateModify = new Date();
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
