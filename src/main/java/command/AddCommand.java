package command;

import task.TaskList;
import ui.Ui;
import storage.Storage;

import exception.DukeException;

/**
 * Represents a specified command as AddCommand by extending the <code>Command</code> class.
 * Adds various specified type of tasks into the taskList. e.g event
 * Responses with the result.
 */
public class AddCommand extends Command {
    private String description;
    private String ddl;
    private String timePiece;

    /**
     * Constructs an <code>AddCommand</code> object
     * with all components of the added task.
     *
     * @param commandType The commandType of the added task.
     * @param description The description of the added task.
     * @param ddl The due of the added task(if applicable).
     * @param timePiece The time period of the added task.
     */
    public AddCommand(String commandType, String description, String ddl, String timePiece) {
        super(commandType);
        this.description = description;
        this.ddl = ddl;
        this.timePiece = timePiece;
    }

    /**
     * Lets the taskList of Duke add the task with the given information and
     * updates content of storage file according to new taskList.
     * Responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException If exceptions occur when adding tasks or updating storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
        switch (super.commandType) {
            case "todo":
                tasks.addToDo(description);
                break;
            case "deadline":
                tasks.addDeadline(description, ddl);
                break;
            case "event":
                tasks.addEvent(description, timePiece);
                break;
        }
        storage.update(tasks.toStorageStrings());

        ui.println("Got it. I've added this task:");
        ui.println(tasks.getTaskInfo(tasks.getSize() - 1));
        ui.println("Now you have " + tasks.getSize() + " tasks in the list.");
    }


}
