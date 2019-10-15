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

    public void execute(TaskList taskList, Ui ui, Storage storage){
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
     * @param StatShortcut the new shortcut
     */
    public static void setStatsShortcut(String StatShortcut) {
        StatsCommand.StatsShortcut = StatShortcut;
    }

}
