package models.tasks;

import models.commands.IDateSettable;

import java.io.Serializable;

public interface ITask extends Serializable, IDateSettable {
    String getStatusIcon();

    void markAsDone();

    String getDescription();

    String getInitials();

    String getDateTime();
}
