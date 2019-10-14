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

    /**
     * static variable used for shortcut
     */
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
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(TodoCommand.todoShortcut.length());
        }
        else {
            userSubstring = user.substring(4);
        }
        if (userSubstring.isBlank()) {
            throw new EmptyTodoException();
        }
        else {
            TodoTask newTask = new TodoTask(user.substring(TodoCommand.todoShortcut.length()).trim());
            tasks.add(newTask);
            storage.save(tasks.getList());
            ui.display("\t Got it. I've added this task:\n\t   "
                    + newTask.toString() +
                    "\n\t Now you have " + tasks.size() + " tasks in the list.");
        }
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getTodoShortcut() {
        return todoShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param todoShortcut the new shortcut
     */
    public static void setTodoShortcut(String todoShortcut) {
        TodoCommand.todoShortcut = todoShortcut;
    }
}
