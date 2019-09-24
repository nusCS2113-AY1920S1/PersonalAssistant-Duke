package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Deadline;
import compal.tasks.TaskList;

import java.util.Scanner;

/**
 * Executes user command "deadline".
 */
public class DeadlineCommand extends Command implements CommandParser {

    private final String TOKEN = "/by";
    private TaskList taskList;

    /**
     * Constructs DeadlineCommand object.
     *
     * @param d Duke.
     */
    public DeadlineCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a Deadline into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user input after "deadline" is empty.
     */
    @Override
    public void Command(String userIn) throws Duke.DukeException {
        Scanner scanner = new Scanner(userIn);

        if (scanner.hasNext()) {
            String event = scanner.next();
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            taskList.addTask(new Deadline(description, date));
            int arrSize = taskList.arrlist.size() - 1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);
        } else {
            duke.ui.printg("InputError: Required input for deadline command!");
            throw new Duke.DukeException("InputError: Required input for deadline command!");
        }
    }
}
