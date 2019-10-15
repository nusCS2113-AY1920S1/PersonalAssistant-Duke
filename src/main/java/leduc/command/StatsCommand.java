package leduc.command;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.Task;
import java.util.ArrayList;

public class StatsCommand extends Command{
    public StatsCommand(String user){
        super(user);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage){
        double numComplete = 0.0;
        double numTasks = taskList.size();
        double numIncomplete = 0.0;
        float percentComplete = 0;
        double numTodos = 0.0;
        double numDeadlines = 0.0;
        double numEvents = 0.0;
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
                "Percent Complete: " + percentComplete + "%" + "\n";
        ui.display(message);
    }

}
