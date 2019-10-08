package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.commons.Compal;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Executes user command "list".
 */
public class ListCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs ListCommand object.
     *
     * @param d Compal.
     */
    public ListCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Lists the tasks currently in taskList.
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void parseCommand(String userIn) {
        Comparator<Task> compareByDateTime = Comparator.comparing(Task::getDate);
        ArrayList<Task> toList = taskList.arrlist;
        Collections.sort(toList, compareByDateTime);
        int count = 1;
        compal.ui.clearPrimary();
        compal.ui.printg("Here are the tasks in your list:", "verdana", 15, Color.BLUEVIOLET);
        for (Task t : toList) {
            compal.ui.printg(count++ + "." + t.toString());
        }
    }
}
