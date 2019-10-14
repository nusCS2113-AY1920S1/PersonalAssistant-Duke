package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.commons.Compal;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

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
     * Additionally set view state to set by user.
     *
     * @param userIn User string input.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (scanner.hasNext()) {
            String dateInput = getDate(scanner.nextLine());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Boolean isEmpty = true;

            for (Task task : taskList.arrlist) {
                Date convertDate = task.getDate();
                String compareDate = formatter.format(convertDate);
                if (compareDate.matches(dateInput)) {
                    if (isEmpty) {
                        compal.ui.printg("On " + dateInput + " you have the task below:");
                    }
                    compal.ui.printg(task.toString());
                    isEmpty = false;
                }
            }

            if (isEmpty) {
                compal.ui.printg("No task found on " + dateInput);
            } else {
                compal.ui.dateState = dateInput;
                compal.ui.dateViewRefresh(compal.ui.dateState);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
