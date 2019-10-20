package leduc.command;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.Task;
import java.util.ArrayList;
/**
 * Represents a Remind Command.
 * Allow to remind user of upcoming tasks in the list.
 */

public class RemindCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String remindShortcut = "remind";
    /**
     * Constructor of FindCommand.
     * @param user String which represent the input string of the user.
     */
    public RemindCommand(String user){
        super(user);
    }
    /**
     * Returns a boolean false as it is a remind command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }




    /**
     * Allow to remind user of upcoming tasks.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */

    public void execute(TaskList tasks, Ui ui, Storage storage){
        ArrayList<Task> filteredTasklist = tasks.filterTasks(tasks);
        ArrayList<Task> extractedTodo = tasks.extractTodo(tasks);
        TaskList sortedTasks = new TaskList(tasks.sort(filteredTasklist, extractedTodo));
        String result = "";

        if (sortedTasks.size() > 0) {
            for (int i = 0; i < sortedTasks.size(); i++) {//prints first 3 tasks in the sorted taskList
                if (i < 3) {
                    result += sortedTasks.displayOneElementList(i);
                }
            }
            System.out.println(result);
        }
        else{
            ui.display("\t There is no upcoming tasks in your list");
        }

    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getRemindShortcut() {
        return remindShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param remindShortcut the new shortcut
     */
    public static void setRemindShortcut(String remindShortcut) {
        RemindCommand.remindShortcut = remindShortcut;
    }
}
