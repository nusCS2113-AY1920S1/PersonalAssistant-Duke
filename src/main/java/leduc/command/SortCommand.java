package leduc.command;

import leduc.Ui;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a sort command.
 * Allows to sort the task by date or description
 */
public class SortCommand extends Command {

    /**
     * Constructor of leduc.command.SortCommand
     * @param user String which represent the input string of the user.
     */
    public SortCommand(String user){
        super(user);
    }

    /**
     * Execution of leduc.command.SortCommand: Allows to sort the task list ( by description or by date).
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws MeaninglessException  Exception caught when the input string could not be interpreted.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FileException, MeaninglessException {
        String sort = user.substring(5).trim();
        if (sort.equals("date")){
            ArrayList<Task> filteredTasklist = tasks.filterTasks(tasks);
            ArrayList<Task> extractedTodo = tasks.extractTodo(tasks);
            // The todo task list is sort by description
            extractedTodo.sort(Comparator.comparing(Task::getTask));
            tasks.setList(tasks.sort(filteredTasklist, extractedTodo));
        }
        else if (sort.equals("description")){
            tasks.getList().sort(Comparator.comparing(Task::getTask));
        }
        else{
            throw new MeaninglessException();
        }
        storage.save(tasks.getList());
        ui.display("\t This is the new task list order: ");
        ListCommand listCommand = new ListCommand(user);
        listCommand.execute(tasks,ui,storage);
    }

    /**
     * Returns is false for a leduc.command.ByeCommand.
     * @return False
     */
    public boolean isExit(){
        return false;
    }
}
