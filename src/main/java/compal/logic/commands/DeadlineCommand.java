package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Deadline;
import compal.tasks.Event;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.util.Date;
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
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            taskList.addTask(new Deadline(description, date));
        } else {
            throw new Duke.DukeException(sadFace + " OOPS!!! The description of a " + event + " cannot be empty.");
        }
    }


}
