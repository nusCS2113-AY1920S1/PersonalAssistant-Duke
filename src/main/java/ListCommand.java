/**
 * Represents a List Command.
 * Allow to display all the tasks contained in the tasks list.
 */
public class ListCommand extends Command {
    /**
     * Constructor of ListCommand.
     * @param user String which represent the input string of the user.
     */
    public  ListCommand(String user){
        super(user);
    }

    /**
     * Allow to displau all the tasks contained in the tasks list.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser){
        if (tasks.size() != 0) {
            ui.displayList(tasks);
        }
        else {
            ui.display("\t There is any task yet ");
        }
    }

    /**
     * Returns a boolean false as it is a list command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
