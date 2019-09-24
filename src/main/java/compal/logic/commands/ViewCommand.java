package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.compal.Compal;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Executes user command "view".
 */
public class ViewCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs ViewCommand object.
     *
     * @param d Compal.
     */
    public ViewCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Displays the tasks available on the user input date.
     *
     * @param userIn User string input.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        String dateInput = scanner.next();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        String formatDate = null;
        try {
            date = formatter.parse(dateInput);
            formatDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (taskList.arrlist.isEmpty()) {
            compal.ui.printg("No task.");
        }

        Boolean isEmpty = true;

        for (Task task : taskList.arrlist) {
            Date convertDate = task.getDate();
            String compareDate = formatter.format(convertDate);
            if (compareDate.matches(formatDate)) {
                if (isEmpty) {
                    compal.ui.printg("On " + formatDate + " you have the task below:");
                }
                compal.ui.printg(task.toString());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            compal.ui.printg("No task found on " + formatDate);
        }
    }
}
