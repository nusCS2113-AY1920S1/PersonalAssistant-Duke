package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import ui.UI;
import task.TaskList;
import list.DegreeList;
import list.DegreeListStorage;

/**
 * ModCommand Class extends the abstract Command class.
 * Called when modifying a task in tasks.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class ModCommand extends Command {
    private String command;
    private String input;
    private DegreeListStorage dd = new DegreeListStorage();
    private int listType = 0;

    public ModCommand(String command, String input) {
        this.command = command;
        this.input = input;
    }

    /**
     * Overwrites default execute to modify task in tasks.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI prints messages
     * @param storage Storage loads and saves files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException DukeException throws exception
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        DegreeList degreesBuffer;
        TaskList tasksBuffer;

        switch (this.command) {
        case "remove":
            this.listType = 1;

            degreesBuffer = lists.deepClone();
            memento = new Memento(degreesBuffer);

            lists.delete(this.input, this.dd);
            break;
        case "done":
            this.listType = 0;

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.markDone(this.input);
            break;
        case "delete":
            this.listType = 0;

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.banishDelete(this.input);
            break;
        case "select":
            this.listType = 0;

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.select(this.input);
            break;
        case "snooze":
            this.listType = 0;

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.snoozeTask(this.input);
            break;
        default:
            throw new DukeException("Invalid ModCommand");
        }
    }

    /**
     * Overwrites default unexecute to remove the modification of taskList and degreeList.
     * Recalls the previous state of the taskList/degreeList via memento.
     * It then returns this state to the current state of the taskList/degreeList.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI.UI prints messages
     * @param storage Storage.Storage loads and saves files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException
     */
    @Override
    public void unExecute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        if (this.listType == 0) {
            TaskList tasksBuffer = memento.getTaskState();
            tasks.clear();
            for (int i = 0; i < tasksBuffer.size(); i++) {
                tasks.add(tasksBuffer.get(i));
            }
        } else {
            DegreeList degreesBuffer = memento.getDegreeState();
            lists.clear();
            for (int i = 0; i < degreesBuffer.size(); i++) {
                lists.add(degreesBuffer.get(i));
            }
        }
    }
}
