package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;


//@@author talesrune
/**
 * Representing a command that adds or updates notes an existing task.
 */
public class DeleteNotesCommand extends Command {
    protected int index;

    /**
     * Creates a command with the specified parameters to add or update notes.
     *

     * @param index The index of the task.
     */
    public DeleteNotesCommand(int index) {
        this.index = index;
    }

    /**
     * Executes a command that adds or updates the notes of the task in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added or updated successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        String deletedNotes = items.get(index).getNotes();
        items.get(index).deleteNotes();
        ui.showDeleteNotes(items, index, deletedNotes);
    }

    /**
     * Executes a command that adds or updates the notes of the task in task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added or updated successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String deletedNotes = items.get(index).getNotes();
        items.get(index).deleteNotes();
        String str = ui.showDeleteNotesGui(items, index, deletedNotes);
        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}