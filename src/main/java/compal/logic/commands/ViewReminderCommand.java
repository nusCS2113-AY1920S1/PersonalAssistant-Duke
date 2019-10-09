package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.commons.Compal;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import javafx.scene.paint.Color;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;


/**
 * Executes user command "reminder".
 */
public class ViewReminderCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs ViewReminderCommand object.
     *
     * @param d Compal.
     */
    public ViewReminderCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Lists all tasks that are incomplete and due in number of days input by user,
     * or by default 7 days, as well as tasks with reminders set as true.
     * Will print colour-coded and sorted by importance/priority.
     *
     * @param userIn Entire user input string.
     * @throws ParseException If date is in invalid format.
     */
    @Override
    public void parseCommand(String userIn) throws ParseException {
        int numberOfDays = 7;
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (scanner.hasNext()) {
            String days = scanner.next();
            numberOfDays = Integer.parseInt(days);
        }

        ArrayList<Task> reminder = new ArrayList<>();
        Date currentDate = java.util.Calendar.getInstance().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(currentDate);
        c.add(Calendar.DATE, numberOfDays);
        Date dateAfter = c.getTime();

        c.setTime(currentDate);
        Date dateToday = c.getTime();

        for (Task t : taskList.arrlist) {
            Date deadline = t.getDate();
            if (deadline != null && !t.isDone && deadline.after(dateToday)
                    && (deadline.before(dateAfter) || t.hasReminder())) {
                System.out.println(deadline);
                t.calculateAndSetPriorityScore();
                reminder.add(t);
            }
        }

        //sort/compare by task priority score
        Comparator<Task> compareByDateTime = (Task t1, Task t2) -> {
            return Long.compare(t2.getPriorityScore(), t1.getPriorityScore());
        };
        Collections.sort(reminder, compareByDateTime);

        //clear secondary window
        compal.ui.clearSecondary();

        //display the results
        if (reminder.isEmpty()) {
            compal.ui.printg("You currently have no tasks that have reminders set or are due within "
                            + numberOfDays + " days!",
                    "verdana",15, Color.DARKGREEN);
        } else {
            int counter = 1;
            for (Task t : reminder) {
                if (t.getPriority().equals(Task.Priority.high)) {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.RED);
                    compal.ui.printSecondaryg(counter + ". " + t.toString(),"verdana",15, Color.RED);
                } else if (t.getPriority().equals(Task.Priority.medium)) {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.ORANGE);
                    compal.ui.printSecondaryg(counter + ". " + t.toString(),"verdana",15, Color.ORANGE);
                } else {
                    //compal.ui.printg(counter + ". " + t.toString(),"verdana",15, Color.GREEN);
                    compal.ui.printSecondaryg(counter + ". " + t.toString(),"verdana",15, Color.GREEN);
                }
                counter++;
            }
        }
    }


}