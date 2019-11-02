package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import list.DegreeListStorage;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.IOException;

public class SwapCommand extends Command{
    private String command;
    private String input;
    private DegreeListStorage dd = new DegreeListStorage();
    private int listType = 0;

    public SwapCommand(String command, String input) {
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
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        DegreeList degreesBuffer;

        switch (this.command) {
        case "swap":
            this.listType = 1; //1 for degree list

            degreesBuffer = lists.deepClone();
            memento = new Memento(degreesBuffer);
            lists.swap(this.input, this.dd);
            break;
        default:
            throw new DukeException("Invalid SwapCommand");
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
     * @throws DukeException Throws the wrong amount of arguments.
     */
    @Override
    public void unExecute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreeManager) throws DukeException {
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
