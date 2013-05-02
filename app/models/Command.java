package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 2.3.2013
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Command extends Model {

    @Id
    public Long id;
    public Long pinId;
    public int  pinStatus;
    @ManyToOne
    public HomeUser commander;
    public Date dateCreate;
    public Date dateModify;

    public static Model.Finder<Long, Command> find = new Model.Finder<Long, Command>(Long.class, Command.class);


    public Command(Long pinId, int pinStatus, HomeUser commander) {
        this.pinId = pinId;
        this.pinStatus = pinStatus;
        this.commander = commander;
        this.dateCreate = new Date();
        this.dateModify = new Date();
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
