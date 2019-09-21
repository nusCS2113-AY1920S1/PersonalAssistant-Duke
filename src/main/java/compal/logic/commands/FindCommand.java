package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FindCommand extends Command implements CommandParser {

    private TaskList taskList;

    public FindCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    @Override
    public void Command(String userIn) throws Duke.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        String searchTerm = scanner.next();

        if (taskList.arrlist.isEmpty()) {
            duke.ui.printg("No task to find.");
        }
        Boolean isEmpty = true;
        for (Task task : taskList.arrlist) {
            if (task.getDescription().contains(searchTerm)) {
                duke.ui.printg(task.getDescription());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            duke.ui.printg("Cannot find  " + searchTerm);
        }
    }
}
