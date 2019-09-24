package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.compal.Compal;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Executes user command "reminder".
 */
public class ReminderCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs ReminderCommand object.
     *
     * @param d Compal.
     */
    public ReminderCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Lists all tasks that are incomplete and due in 7 days, as well as tasks with reminders set as true.
     *
     * @param userIn Entire user input string.
     * @throws ParseException If date is in invalid format.
     */
    @Override
    public void parseCommand(String userIn) throws ParseException {
        compal.ui.printg("Reminder: \n");
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
            if (deadline != null && !t.isDone && deadline.after(dateToday)
                    && (deadline.before(dateOneWeekAfter) || t.hasReminder())) {
                System.out.println(deadline);
                reminder.add(t);
            }
        }

        Comparator<Task> compareByDateTime = (Task t1, Task t2) -> t1.getDate().compareTo(t2.getDate());
        Collections.sort(reminder, compareByDateTime);

        if (reminder.isEmpty()) {
            compal.ui.printg("You currently have no tasks that have reminders set or are due within a week!");
        } else {
            for (Task t : reminder) {
                compal.ui.printg(t.toString());
            }
        }
    }
}
