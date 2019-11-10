package leduc.command;
import leduc.storage.Storage;
import leduc.ui.Ui;
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
     * @param userInput String which represent the input string of the user.
     */
    public RemindCommand(String userInput){
        super(userInput);
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
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage){
        ArrayList<Task> filteredTasklist = tasks.filterTasks(tasks);
        ArrayList<Task> extractedTodo = tasks.extractTodo(tasks);
        ArrayList<Task> sortedList = tasks.sort(filteredTasklist, extractedTodo);
        TaskList sortedTasks = new TaskList(sortedList);

        String result = "";
        int length = sortedTasks.size();
        if (length > 0) {
            int numReturned= 0;
            for (int i = 0; i < length; i++) {//prints first 3 tasks in the sorted taskList
                Task task = sortedTasks.get(i);
                String mark = task.getMark();
                if ((numReturned < 3) && (mark.equals("[X]"))) {
                    result += sortedTasks.displayOneElementList(i);
                    numReturned++;
                }
            }
            ui.display(result);
        }
        else{
            ui.showNoTask();
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
