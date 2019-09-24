package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.DoAfterTasks;
import compal.tasks.TaskList;

import java.util.Scanner;

/**
 * Executes user command "doaftertask".
 */
public class DoAfterCommand extends Command implements CommandParser {

    private static final String TOKEN = "/after";
    private TaskList taskList;

    /**
     * Constructs DoAfterCommand object.
     *
     * @param d Duke
     */
    public DoAfterCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a DoAfterTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user input after "doafter" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Duke.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            taskList.addTask(new DoAfterTasks(description, date));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);
        } else {
            duke.ui.printg("InputError: Required input for DoAfter command!");
            throw new Duke.DukeException("InputError: Required input for DoAfter command!");
        }
    }
}
