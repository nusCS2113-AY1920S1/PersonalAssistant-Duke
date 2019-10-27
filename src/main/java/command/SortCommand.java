package command;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.IOException;
import java.time.LocalDateTime;

public class SortCommand extends Command {
    private String arguments;
    private String command;
    private Memento memento;
    private int listType = 0; //0 for task list, 1 for degree list

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
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists) throws DukeException {
        if (this.arguments.matches("priority")){
            System.out.println(tasks.get(9).getDueDate());
            tasks.get(9).setTaskPriority(5);
            System.out.println(tasks.get(9).getTaskPriority());
            tasks.sortPriority();
        }
    }
}
