package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Task;
import compal.tasks.TaskList;

public class ListCommand extends Command implements CommandParser {

    private TaskList taskList;

    public ListCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Handles the list command which lists the tasks currently in COMPal.Duke's tracking
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string
     */
    @Override
    public void Command(String userIn) {
        int count = 1;
        duke.ui.printg("Here are the tasks in your list:");
        for (Task t : taskList.arrlist) {
            duke.ui.printg(count++ + "." + t.toString());
        }
    }
}
