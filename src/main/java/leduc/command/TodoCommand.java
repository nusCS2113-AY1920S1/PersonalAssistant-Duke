package leduc.command;

import leduc.Parser;
import leduc.command.Command;
import leduc.exception.EmptyTodoException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.TodoTask;

import java.io.IOException;

/**
 * Represents a Todo leduc.command.Command.
 * Allows to add the todo task in the tasks list and the data file.
 */
public class TodoCommand extends Command {
    /**
     * Constructor of leduc.command.TodoCommand.
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
     * @param parser leduc.Parser which deals with making sense of the user command.
     * @throws EmptyTodoException Exception caught when the description of the todo list is not given by the user.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws EmptyTodoException{
        if (user.substring(4).isBlank()) {
            throw new EmptyTodoException(ui);
        }
        else {
            tasks.add(new TodoTask(user.substring(4).trim()));
            TodoTask newTask = (TodoTask) tasks.get(tasks.size() - 1);
            try {
                storage.getAppendWrite().write(tasks.size() + "//" + newTask.getTag() + "//" +
                        newTask.getMark() + "//" + newTask.getTask()+"\n");
            }
            catch (IOException e){
                ui.display("\t IOException:\n\t\t error when writing a todoTask to file");
            }
            ui.display("\t Got it. I've added this task:\n\t   "
                    + newTask.getTag() + newTask.getMark() + newTask.getTask() +
                    "\n\t Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Returns a boolean false as it is a Todo command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
