package models.temp.tasks;

import models.temp.commands.IDateSettable;

import java.io.Serializable;
import java.util.Date;

public interface ITask extends Serializable, IDateSettable {
    String getStatusIcon();

    void markAsDone();

    String getDescription();

    String getInitials();

    String getDateTime();

    String getFullDescription();

    Date getDateTimeObject();
}
