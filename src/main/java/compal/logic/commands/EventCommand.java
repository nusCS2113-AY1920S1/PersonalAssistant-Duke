package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Event;
import compal.tasks.TaskList;

import java.util.Scanner;

/**
 * Executes user command "event".
 */
public class EventCommand extends Command implements CommandParser {

    private static final String TOKEN = "/at";
    private TaskList taskList;

    /**
     * Constructs EventCommand object.
     *
     * @param d Duke.
     */
    public EventCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     *
     * Adds a single ToDo to the tasklist and print out confirmation for the user.
     *
     * @param userIn Entire String input by the user.
     */

    /**
     * Adds an Event into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user input after "event" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Duke.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            String time = getTime(restOfInput);
            taskList.addTask(new Event(description, date, time));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);
        } else {
            duke.ui.printg("InputError: Required input for Event command!");
            throw new Duke.DukeException("InputError: Required input for Event command!");
        }
    }
}
