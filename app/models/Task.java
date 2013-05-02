package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: prmejc
 * Date: 3.3.2013
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Task extends Model {

    @Id
    public Long taskId;
    @ManyToOne
    public HomeUser commander;
    @ManyToOne
    public Pin pin;
    public int status;
    public Date executeTime;
    public Date dateCreate;
    public Date dateModify;

    public static Model.Finder<Long, Task> find = new Finder<Long, Task>(Long.class, Task.class);

    public Task(HomeUser commander, Pin pin, int status, Date executeTime) {
        this.commander = commander;
        this.pin = pin;
        this.status = status;
        this.executeTime = executeTime;
        dateCreate = new Date();
        dateModify = new Date();
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
