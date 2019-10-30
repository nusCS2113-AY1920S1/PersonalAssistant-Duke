package duke.task;

//@@author gervaiseang

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a reminder class which checks for tasks due soon
 */
public class Reminders implements Serializable {
    //protected SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    //private ArrayList<String> reminderList;

    public TaskList getReminders(int remindDaysFrom, TaskList items) {
        LocalDateTime currentDateTime = LocalDateTime.now(); // gets the current date and time
        LocalDateTime remindByDateTime = currentDateTime.plusDays(remindDaysFrom);
        TaskList myTaskList = new TaskList();
        items.getTasks().forEach(task -> {
           if(task.getDateTime()!= null) {
               String str = task.getDateTime();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
               LocalDateTime taskDue = LocalDateTime.parse(str, formatter);
               if(taskDue.isAfter(currentDateTime) && taskDue.isBefore(remindByDateTime)) {
                    myTaskList.add(task);
                }
            }
        });
        return myTaskList;
    }
}
