package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ReminderCommand extends Command implements CommandParser {

    private TaskList taskList;

    public ReminderCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void parseCommand(String userIn) {
        duke.ui.printg("Reminder: \n");
        ArrayList<Task> reminder = new ArrayList<>();
        Date currentDate = java.util.Calendar.getInstance().getTime();
        Calendar c = Calendar.getInstance();

        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Date dateOneWeekAfter = c.getTime();

        c.setTime(currentDate);
        Date dateToday = c.getTime();

        for (Task t : taskList.arrlist) {
            Date deadline = t.getDate();
            if ((deadline != null && !t.isDone && deadline.after(dateToday)
                    && deadline.before(dateOneWeekAfter)) || t.isHasReminder()) {
                System.out.println(deadline);
                reminder.add(t);
            }
        }

        Comparator<Task> compareByDateTime = (Task t1, Task t2) -> t1.getDate().compareTo(t2.getDate());
        Collections.sort(reminder, compareByDateTime);

        if (reminder.isEmpty()) {
            duke.ui.printg("You currently have no tasks that have reminders set or are due within a week!");
        } else {
            for (Task t : reminder) {
                duke.ui.printg(t.toString());
            }
        }
    }
}
