/*
@@author woblek
 */
package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import storage.Storage;
import task.TaskList;
import ui.UI;

public class SortCommand extends Command {
    private String arguments;
    private String command;
    private Memento memento;

    /**
     * Creates AddCommand.
     * @param command command type to be used.
     * @param arguments to be added.
     */
    public SortCommand(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }


    @Override
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager mydegrees) throws DukeException {
        TaskList tasksBuffer;

        tasksBuffer = tasks.deepClone();
        memento = new Memento(tasksBuffer);
        if (this.arguments.matches("by priority")){
            tasks.sortbyPriority();
        }
        else if (this.arguments.matches("by date")){
            tasks.sortbyDate();
        }
        else{
            throw new DukeException("That is not a valid way to sort your tasks!\n" +
                    "Try typing:\n" +
                    "   sort by priority\n" +
                    "   sort by date\n" +
                    "   sort by degree" );
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
        TaskList tasksBuffer = memento.getTaskState();
        tasks.clear();

        for (int i = 0; i < tasksBuffer.size(); i++) {
            tasks.add(tasksBuffer.get(i));
        }
    }
}
