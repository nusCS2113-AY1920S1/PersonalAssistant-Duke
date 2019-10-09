package leduc.command;

import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;

/**
 * Represents a Find Command.
 * Allow to find a specific task from the task list.
 */
public class FindCommand extends Command {
    public static String findShortcut = "find";
    /**
     * Constructor of FindCommand.
     * @param user String which represent the input string of the user.
     */
    public FindCommand(String user){
        super(user);
    }
    /**
     * Allow to find tasks from the task list.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage){
        String find = user.substring(5);
        String result = "";
        for ( int i = 0 ; i< tasks.size() ; i++){
            if ( tasks.get(i).getTask().contains(find)){
                result += tasks.displayOneElementList(i);
            }
        }
        if ( result.isEmpty()){
            ui.display("\t There is no matching tasks in your list");
        }
        else {
            ui.display("\t Here are the matching tasks in your list: \n" + result );
        }
    }

    public static String getFindShortcut() {
        return findShortcut;
    }

    public static void setFindShortcut(String findShortcut) {
        FindCommand.findShortcut = findShortcut;
    }
}
