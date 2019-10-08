package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.DoAfterTasks;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

/**
 * Executes user command "doaftertask".
 */
public class DoAfterCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs DoAfterCommand object.
     *
     * @param d Compal
     */
    public DoAfterCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a DoAfterTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user input after "doafter" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            taskList.addTask(new DoAfterTasks(description, priority, date));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            compal.ui.printg(descToPrint);
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
