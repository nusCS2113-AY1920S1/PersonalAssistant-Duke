package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

/**
 * Executes user command "delete".
 */
public class DeleteCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs DeleteCommand object.
     *
     * @param d Compal
     */
    public DeleteCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Deletes a task based on user task number input and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user task number input is invalid.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        //Compal.ui.printg(userIn);
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();

            int indexToRemove = Integer.parseInt(restOfInput.trim()) - 1;
            int maxLimit = taskList.arrlist.size();

            if (indexToRemove < 0 || indexToRemove >= maxLimit) {
                compal.ui.printg(MESSAGE_INVALID_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_RANGE);
            }

            Task toRemove = taskList.arrlist.get(indexToRemove);

            //free up the task's unique ID for future use
            taskList.unsetId(toRemove.getId());

            Date removeDate = toRemove.getDate();
            String removeDesc = toRemove.toString();

            taskList.removeTask(indexToRemove);
            compal.ui.secondaryScreenRefresh(removeDate);
            compal.ui.printg("Noted. I've removed this task:\n" + removeDesc);
            compal.taskStorage.saveData(taskList.arrlist);
            compal.ui.showSize();


            //Compal.tasklist.deleteTask(userIn);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
