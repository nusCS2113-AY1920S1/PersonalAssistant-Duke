package models.tasks;

import models.commands.IDateSettable;

import java.io.Serializable;
import java.text.ParseException;
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
