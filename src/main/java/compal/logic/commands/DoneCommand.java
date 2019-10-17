package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.TaskList;

import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_TASK_NUMBER;

/**
 * Executes user command "done".
 */
public class DoneCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs Done object.
     *
     * @param d Compal.
     */
    public DoneCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Marks task as done based on user task number input and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user task number input is missing.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        int maxSize = taskList.arrlist.size();
        if (scanner.hasNextLine()) {
            String restOfInput = scanner.nextLine();
            int toMark = Integer.parseInt(restOfInput.trim()) - 1;
            if (toMark >= 0 && toMark < maxSize) {
                taskList.arrlist.get(toMark).markAsDone();
                String desc = taskList.arrlist.get(toMark).toString();
                compal.ui.printg("Nice! I've marked this task as done: \n" + desc);
                compal.taskStorage.saveData(taskList.arrlist);
                compal.ui.secondaryScreenRefresh(taskList.arrlist.get(toMark).getDate());
            } else {
                compal.ui.printg(MESSAGE_INVALID_TASK_NUMBER);
                throw new Compal.DukeException(MESSAGE_INVALID_TASK_NUMBER);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
        //Compal.tasklist.taskDone(userIn);
    }
}
