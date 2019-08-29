package models;

import java.io.Serializable;

public interface ITask extends Serializable {
    String getStatusIcon();

    void markAsDone();

    String getDescription();

    String getInitials();
}
