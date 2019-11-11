package duke.command;

import duke.enums.Numbers;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.FixedDuration;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune
/**
 * Represents a command that updates an existing task.
 */
public class UpdateCommand extends Command {
    protected String taskDesc;
    protected String dateDesc;
    protected String typeDesc;
    protected int typeOfUpdate;
    protected int index;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a command with the specified parameters to update.
     *
     * @param taskDesc The task description to be updated.
     * @param dateDesc The date/time to be updated.
     * @param typeDesc The type of task to be updated.
     * @param typeOfUpdate The type of update.
     * @param index The index to be updated.
     */
    public UpdateCommand(String taskDesc, String dateDesc, String typeDesc, int typeOfUpdate, int index) {
        this.taskDesc = taskDesc;
        this.dateDesc = dateDesc;
        this.typeDesc = typeDesc;
        this.typeOfUpdate = typeOfUpdate;
        this.index = index;
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
        String str = "";
        try {
            if (typeOfUpdate == Numbers.ONE.value) {
                items.get(index).setDescription(taskDesc);
            } else if (typeOfUpdate == Numbers.TWO.value) {
                if (items.get(index) instanceof Todo || items.get(index) instanceof FixedDuration) {
                    return "     (>_<) OOPS!!! This task does not have date and time!";
                }
                items.get(index).setDateTime(dateDesc);
            } else if (typeOfUpdate == Numbers.THREE.value) {
                Task newtaskObj;
                if (typeDesc.equals("todo")) {
                    if (items.get(index) instanceof Todo) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (Todo)";
                    } else {
                        newtaskObj = new Todo(items.get(index).getDescription());
                    }
                } else if (typeDesc.equals("deadline") || typeDesc.equals("dl")) {
                    if (items.get(index) instanceof Deadline) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (Deadline)";
                    } else {
                        SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
                        Date currentDate = new Date();
                        String datetimeStr = datetimeFormat.format(currentDate);
                        newtaskObj = new Deadline(items.get(index).getDescription(), datetimeStr);
                        str += "     The date and time will be set to current date/time by default.\n";
                    }
                } else if (typeDesc.equals("fixedduration") || typeDesc.equals("fd")) {
                    if (items.get(index) instanceof FixedDuration) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (FixedDuration)";
                    } else {
                        str += "     The duration will be set to 1 hour by default.\n";
                        newtaskObj = new FixedDuration(items.get(index).getDescription(), Numbers.ONE.value, "hour");
                    }
                } else {
                    return "     (>_<) OOPS!!! You are entered an invalid task type!";
                }
                items.setTaskType(index, newtaskObj);
            }
            str += Ui.showUpdateGui(items, index);
        } catch (ParseException e) {
            e.printStackTrace();
            logr.log(Level.WARNING,"Error found when updating task's date", e);
            str = "     Error found when updating task's date, please use this format \"dd/MM/yyyy HHmm\"";
        } catch (Exception e) {
            ui.showErrorMsg(e.getMessage());
            str = "     New error found when updating task, please fix.";
            logr.log(Level.SEVERE,"New error found when updating task", e);

        }
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