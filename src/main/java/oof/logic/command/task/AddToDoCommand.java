package oof.logic.command.task;

import java.util.ArrayList;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.commons.exceptions.command.MissingArgumentException;
import oof.model.university.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.storage.StorageManager;

/**
 * Represents a Command to add Todo objects to TaskList.
 */
public class AddToDoCommand extends Command {

    public static final String COMMAND_WORD = "todo";
    private ArrayList<String> arguments;
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_DATE = 1;
    private static final int ARRAY_SIZE_DATE = 2;
    private static final int DESCRIPTION_LENGTH_MAX = 20;

    /**
     * Constructor for AddTodoCommand.
     *
     * @param arguments Command inputted by user.
     */
    public AddToDoCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Adds a todo task to taskList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (arguments.get(INDEX_DESCRIPTION).equals("")) {
            throw new MissingArgumentException("OOPS!!! The todo needs a description.");
        } else if (arguments.size() < ARRAY_SIZE_DATE || arguments.get(INDEX_DATE).equals("")) {
            throw new MissingArgumentException("OOPS!!! The todo needs a date.");
        }
        String description = arguments.get(INDEX_DESCRIPTION);
        String date = parseDate(arguments.get(INDEX_DATE));
        if (exceedsMaxLength(description, DESCRIPTION_LENGTH_MAX)) {
            throw new InvalidArgumentException("OOPS!!! Task description exceeds maximum length of "
                    + DESCRIPTION_LENGTH_MAX + "!");
        } else if (!isDateValid(date)) {
            throw new InvalidArgumentException("OOPS!!! The date is invalid.");
        } else {
            Task task = new Todo(description, date);
            taskList.addTask(task);
            ui.addTaskMessage(task, taskList.getSize());
            storageManager.writeTaskList(taskList);
        }
    }

}

