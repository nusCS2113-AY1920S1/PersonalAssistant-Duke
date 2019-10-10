package command;

import task.Task;
import ui.UI;
import task.TaskList;
import list.DegreeList;
import storage.Storage;
import exception.DukeException;

/**
 * PrintCommand Class extends the abstract Command class.
 * Called when printing items in tasks
 *
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class PrintCommand extends Command {
    private String command;

    public PrintCommand(String command) {
        this.command = command;
    }

//    protected PrintCommand() {
//    }

    /**
     * overwrites default execute to print task in tasks.
     *
     * @param tasks   TasksList Object being used currently
     * @param ui      UI in charge of printing messages
     * @param storage Storage in charge of loading and saving files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException DukeException thrown when unable to execute
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists) {
        if(this.command.matches("list")) {
            tasks.print();
        }
        if(this.command.matches("choices")) {
            lists.print();
        }
    }
}