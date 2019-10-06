package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

/**
 * Representing a command that updates an existing task.
 */
public class UpdateCommand extends Command {
    protected String taskDesc;
    protected String dateDesc;
    protected int typeOfUpdate;
    protected int index;

    /**
     * Creates a command with the specified parameters to update.
     *
     * @param taskDesc The task description to be updated.
     * @param dateDesc The date/time to be updated.
     * @param typeOfUpdate The type of update.
     * @param index The index to be updated.
     */
    public UpdateCommand(String taskDesc, String dateDesc, int typeOfUpdate, int index) {
        this.taskDesc = taskDesc;
        this.dateDesc = dateDesc;
        this.typeOfUpdate = typeOfUpdate;
        this.index = index;
    }

    /**
     * Executes a command that updates the task in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is updated successfully.
     */
    //@Override
    public void execute(TaskList items, Ui ui) {
        try {
            if (typeOfUpdate == 1) {
                items.get(index).setDescription(taskDesc);
            } else if (typeOfUpdate == 2) {
                items.get(index).setDateTime(dateDesc);
            }
            ui.showUpdate(items, index);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ui.showErrorMsg(e.getMessage());
        }

    }

    /**
     * Executes a command that updates the task in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is updated successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        try {
            if (typeOfUpdate == 1) {
                items.get(index).setDescription(taskDesc);
            } else if (typeOfUpdate == 2) {
                items.get(index).setDateTime(dateDesc);
            }
            ui.showUpdate(items, index);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ui.showErrorMsg(e.getMessage());
        }
        String str = Ui.showUpdateGui(items, index);
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