package leduc.command;

import leduc.Ui;
import leduc.exception.EmptyArgumentException;
import leduc.exception.FileException;
import leduc.exception.MeaninglessException;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a sort command.
 * Allows to sort the task by date or description or priority
 */
public class SortCommand extends Command {
    /**
     * static variable used for shortcut
     */
    private static String sortShortcut = "sort";
    /**
     * Constructor of leduc.command.SortCommand
     * @param user String which represent the input string of the user.
     */
    public SortCommand(String user){
        super(user);
    }

    /**
     * Execution of leduc.command.SortCommand: Allows to sort the task list ( by description or by date or ny priority).
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws MeaninglessException  Exception caught when the input string could not be interpreted.
     * @throws EmptyArgumentException Exception caught when there is no argument
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws FileException, MeaninglessException, EmptyArgumentException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(SortCommand.sortShortcut.length());
        }
        else {
            userSubstring = user.substring(4);
        }
        String sort = userSubstring.trim();
        if(sort.isBlank()) {
            throw new EmptyArgumentException();
        }
        if (sort.equals("date")){
            ArrayList<Task> filteredTasklist = tasks.filterTasks(tasks);
            ArrayList<Task> extractedTodo = tasks.extractTodo(tasks);
            // The todo task list is sort by description
            extractedTodo.sort(Comparator.comparing(Task::getTask));
            tasks.setList(tasks.sort(filteredTasklist, extractedTodo));
        }
        else if (sort.equals("priority")){
            tasks.getList().sort(Comparator.comparingInt(Task::getPriority));
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
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getSortShortcut() {
        return sortShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param sortShortcut the new shortcut
     */
    public static void setSortShortcut(String sortShortcut) {
        SortCommand.sortShortcut = sortShortcut;
    }
}
