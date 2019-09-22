package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Deadline;
import compal.tasks.TaskList;

import java.util.Scanner;

public class DeadlineCommand extends Command implements CommandParser {

    private final String TOKEN = "/by";
    private TaskList taskList;

    public DeadlineCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a single ToDo to the tasklist and print out confirmation for the user.
     *
     * @param userIn Entire String input by the user.
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
            String statusIcon = taskList.arrlist.get(arrSize).getStatusIcon();
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);
        } else {
            duke.ui.printg("InputError: Required input for deadline command!");
            throw new Duke.DukeException("InputError: Required input for deadline command!");
        }
    }


}
