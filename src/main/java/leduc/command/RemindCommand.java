package leduc.command;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.Task;
import leduc.task.EventsTask;
import leduc.task.TodoTask;
import leduc.task.DeadlinesTask;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Represents a Remind Command.
 * Allow to remind user of upcoming tasks in the list.
 */

public class RemindCommand extends Command {
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
     * Extracts all EventsTask/DeadlinesTask into a seperate arraylist. Tasks with/without dates must be seperated prior to sorting
     *@param tasks tasks is the list of tasks
     */
    public static ArrayList filterTasks(TaskList tasks){
        ArrayList<Task> filteredTasklist = new ArrayList<Task>();
        for (int i = 0; i < tasks.size(); i++){
            if (!(tasks.get(i) instanceof TodoTask)){
                filteredTasklist.add(tasks.get(i));
            }
        }
        return filteredTasklist;
    }

    /**
     * Extracts all Todo's into a seperate arraylist. Tasks with/without dates must be separated prior to sorting
     * @param  tasks is the list of tasks
     */
    public static ArrayList extractTodo(TaskList tasks){
        ArrayList<Task> extractedTodos = new ArrayList<Task>();
        for (int i = 0; i < tasks.size(); i++){
            if ((tasks.get(i) instanceof TodoTask)){
                extractedTodos.add(tasks.get(i));
            }
        }
        return extractedTodos;
    }
    /**
     * Helper method which returns the date from any task Object.
     * @param task  Task whos date field will get extracted.
     */
    public static LocalDateTime getDate(Object task){
        if (task instanceof DeadlinesTask) {
            DeadlinesTask deadline = (DeadlinesTask)task;
            return (deadline.getDeadlines()).getD();
        }
        else if (task instanceof EventsTask){
            EventsTask event = (EventsTask)task;

            return(event.getDateFirst()).getD();
        }
        else{
            return null;
        }
    }
    /**
     * Sorts the list of tasks by date.
     * @param filteredTasklist which filters out all Tasks that do not have a date field..
     * @param extractedTodos which is a list of all Todo objects, will get appended to the final sorted list.
     */
    public static ArrayList sort(ArrayList<Task> filteredTasklist, ArrayList<Task> extractedTodos){
        ArrayList<Task> sortedTasks = new ArrayList<Task>();
        for(int i = 0; filteredTasklist.size() > 0; i++){
            Task initialTask = filteredTasklist.get(0);//set initial task
            for(int j = 0; j < filteredTasklist.size(); j++){
                if((getDate(filteredTasklist.get(j)).compareTo(getDate(initialTask))) < 0) {//compare each date to initialdate
                    initialTask = filteredTasklist.get(j);//update if necessary
                }
            }
            sortedTasks.add(initialTask);
            filteredTasklist.remove(initialTask);
        }
        sortedTasks.addAll(extractedTodos);
        return sortedTasks;
    }

    /**
     * Allow to remind user of upcoming tasks.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */

    public void execute(TaskList tasks, Ui ui , Storage storage){

        ArrayList<Task> filteredTasklist = new ArrayList<Task>(filterTasks(tasks));
        ArrayList<Task> extractedTodo = new ArrayList<Task>(extractTodo(tasks));
        TaskList sortedTasks = new TaskList(sort(filteredTasklist, extractedTodo));
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

}
