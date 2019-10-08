package models.temp.tasks;

import java.io.Serializable;
import java.util.Date;

public interface IRecurring extends Serializable {
    int getDaysLeft();

    Date getStartDate();

    String getRecurringDescription();
}
