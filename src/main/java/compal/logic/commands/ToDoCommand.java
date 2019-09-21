package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.TaskList;
import compal.tasks.Todo;

import java.text.ParseException;
import java.util.Scanner;

public class ToDoCommand extends Command implements CommandParser {

    private TaskList taskList;

    public ToDoCommand(Duke d) {
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
        String todo = scanner.next();
        if (scanner.hasNext()) {
            String description = scanner.nextLine();
            taskList.addTask(new Todo(description.trim()));
            int arrSize = taskList.arrlist.size() - 1;
            String statusIcon = taskList.arrlist.get(arrSize).getStatusIcon();
            duke.ui.printg("[T][" + statusIcon + "] " + description);
        } else {
            throw new Duke.DukeException(sadFace + " OOPS!!! The description of a " + todo + " cannot be empty.");
        }
    }
}
