package leduc.command;

import leduc.exception.EmptyTodoException;
import leduc.exception.FileException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.TodoTask;


/**
 * Represents a Todo Command.
 * Allows to add the todo task in the tasks list and the data file.
 */
public class TodoCommand extends Command {

    public static String todoShortcut = "todo";
    /**
     * Constructor of TodoCommand.
     * @param user String which represent the input string of the user.
     */
    public  TodoCommand(String user){
        super(user);
    }

    /**
     * Allow to add the task in the tasks list and the data file.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyTodoException Exception caught when the description of the todo list is not given by the user.
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EmptyTodoException, FileException {
        if (user.substring(4).isBlank()) {
            throw new EmptyTodoException();
        }
        else {
            TodoTask newTask = new TodoTask(user.substring(4).trim());
            tasks.add(newTask);
            storage.save(tasks.getList());
            ui.display("\t Got it. I've added this task:\n\t   "
                    + newTask.toString() +
                    "\n\t Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    public static String getTodoShortcut() {
        return todoShortcut;
    }

    public static void setTodoShortcut(String todoShortcut) {
        TodoCommand.todoShortcut = todoShortcut;
    }
}
