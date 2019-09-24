package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.TaskList;

import java.util.Scanner;

/**
 * Executes user command "done".
 */
public class DoneCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs Done object.
     *
     * @param d Duke.
     */
    public DoneCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Marks task as done based on user task number input and prints confirmation message to user.
     *
     * @param userIn Entire user input string.
     * @throws Duke.DukeException If user task number input is missing.
     */
    @Override
    public void Command(String userIn) throws Duke.DukeException {

        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (scanner.hasNextLine()) {
            String restOfInput = scanner.nextLine();
            int toMark = Integer.parseInt(restOfInput.trim()) - 1;
            taskList.arrlist.get(toMark).markAsDone();
            String desc = taskList.arrlist.get(toMark).toString();
            duke.ui.printg("Nice! I've marked this task as done: \n" + desc);
            duke.storage.saveCompal(taskList.arrlist);
        } else {
            duke.ui.printg("InputError: Required input for done command!");
            throw new Duke.DukeException("InputError: Required input for done command!");
        }
        //duke.tasklist.taskDone(userIn);
    }
}
