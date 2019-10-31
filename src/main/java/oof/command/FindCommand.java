package oof.command;

import java.util.ArrayList;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command that finds and displays specific tasks in the TaskList.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    private String argument;

    /**
     * Constructor for FindCommand.
     *
     * @param argument Command inputted by user.
     */
    public FindCommand(String argument) {
        super();
        this.argument = argument;
    }

    /**
     * Finds the Tasks that contain the keyword(s) specified
     * by the user after processing the Command.
     * Displays the Tasks found after collating them.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storageManager      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        String[] lineSplit = argument.split(" ");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The find command needs a description.");
        }
        String item = lineSplit[1].trim();
        ArrayList<Task> matchedTasks = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            if (taskList.getTask(i).getDescription().contains(item)) {
                matchedTasks.add(taskList.getTask(i));
            }
        }
        ui.printMatchingTasks(matchedTasks);
    }

    /**
     * Checks if input has a description.
     *
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit.length != 1;
    }

}
