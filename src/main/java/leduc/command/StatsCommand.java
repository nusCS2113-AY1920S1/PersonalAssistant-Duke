package leduc.command;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.Task;


public class StatsCommand extends Command{
    /**
     * static variable used for shortcut
     */
    private static String StatsShortcut = "stats";
    public StatsCommand(String user){
        super(user);
    }
    /**
     * Allow to see statistics on their taskList
     * @param taskList leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage){
        //metrics to be analyzed
        double numComplete = 0.0;
        double numTasks = taskList.size();
        float percentComplete;
        double numTodos = 0.0;
        double numDeadlines = 0.0;
        double numEvents = 0.0;
        double numIncomplete = 0.0;
        for (int i = 0 ;i< taskList.size() ; i++ ){
            Task task = taskList.get(i);
            if(task.getMark().equals("[✓]")){
                numComplete++;
            }
            else{
                numIncomplete++;
            }
            if(task.isDeadline()){
                numDeadlines++;
            }
            else if(task.isEvent()){
                numEvents++;
            }
            else if(task.isTodo()){
                numTodos++;
            }
        }
        percentComplete = (float) (numComplete/numTasks) * 100;
        //display metrics
        String message = "Here are some statistics about your task list: \n" +
                "Number of tasks: " + numTasks + "\n" +
                "Number of Todo's : " + numTodos + "\n" +
                "Number of Events: " + numEvents + "\n" +
                "Number of Deadlines: " + numDeadlines + "\n" +
                "Number of Uncompleted Tasks: " + numIncomplete + "\n" +
                "Number of Completed Tasks: " + numComplete + "\n" +
                "Percent Complete: " + percentComplete + "%";
                ui.display(message);
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getStatsShortcut() {
        return StatsShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param StatsShortcut the new shortcut
     */
    public static void setStatsShortcut(String StatsShortcut) {
        StatsCommand.StatsShortcut = StatsShortcut;
    }

}
