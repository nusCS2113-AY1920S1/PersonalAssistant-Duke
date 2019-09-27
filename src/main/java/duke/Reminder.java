package duke;


import duke.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

/**
 * Represents a Reminder class to remind user on upcoming deadlines.
 */
public class Reminder {
    /**
     * Checks Task objects dates to determine if it is due soon.
     *
     * @param taskList TaskList that contains Task objects.
     * @param ui       Ui that is responsible for visual feedback.
     */
    public void checkDeadline(TaskList taskList, Ui ui) {
        try {
            System.out.println("\tReminder these tasks have upcoming deadlines:");
            int count = 1;
            for (int i = 0; i < taskList.getSize(); i++) {
                Task task = taskList.getTask(i);
                if (task.toString().startsWith("[D]")) {
                    String[] lineSplit = task.toString().split("by:");
                    String result = lineSplit[1].substring(0, lineSplit[1].length() - 1); // remove closing bracket
                    result = result.trim();
                    Date dueDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(result);
                    Date now = new Date(); // current date time
                    long diff = dueDate.getTime() - now.getTime(); // difference in time in milliseconds
                    long diffHours = diff / (60 * 60 * 1000);
                    if (diffHours <= 24) {
                        System.out.println("\t" + count + "." + taskList.getTask(i));
                        count++;
                    }
                }
            }
            ui.showLine();
        } catch (ParseException | DateTimeException e) {
            System.out.println("Timestamp given is invalid! Please try again.");
        }
    }
}
