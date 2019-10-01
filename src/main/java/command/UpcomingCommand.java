package command;

import storage.Storage;
import task.Deadline;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * command.Command to show tasks in the upcoming week
 */
public class UpcomingCommand extends Command {

    /**
     * Based on current CPU Date, search for any tasks due in 7 days.
     * @param tasks
     */

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        LocalDateTime dateNow = LocalDateTime.now();
        int counter = 0;
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd");

        System.out.println("Here are your tasks due in the upcoming week:");
        for (int i = 0; i < 7; i++) {
            String formattedDate = dateNow.format(myFormatObj);
            LocalDateTime nextDay = dateNow.plusDays(i);
            String formattedNextDay = nextDay.format(myFormatObj);


            for (int j = 0; j < tasks.size(); j++) {
                if (tasks.get(j).toString().contains(formattedNextDay)) {
                    Date dateBefore = sdf.parse(formattedDate);
                    Date dateAfter = sdf.parse(formattedNextDay);
                    long difference = dateAfter.getTime() - dateBefore.getTime();
                    long daysTo = (difference/(1000*60*60*24));

                    counter++;
                    if (daysTo == 0) {
                        System.out.println("    " + counter + ". " + tasks.get(j).toString() + " -> [Due TODAY]");
                    } else {
                        System.out.println("    " + counter + ". " + tasks.get(j).toString()
                                + " -> [Due in: " + daysTo + " Day(s)]");
                    }
                }
            }
        }
        if (counter == 0) {
            System.out.println("You have 0 tasks due in the upcoming week");
        } else {
            System.out.println("You have " + counter + " tasks due in the upcoming week.");
        }
    }
}
