import java.util.ArrayList;

public class Ui {

    private ArrayList<Task> arrlist;

    public Ui(ArrayList<Task> arrayList){
        arrlist=arrayList;
    }

    /**
     * @Function
     * Simply prints the welcome message for Duke
     */
    public void showWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello there!\n" + logo);
        System.out.println("Hello! I'm Duke\n" +
                "     What can I do for you?");
    }



    /**
     * @Function
     * No Params, No Return Value
     * This function simply shows the number of tasks in the arraylist
     * @UsedIn: tasklist.addTask
     */
    public void showSize(){
        System.out.println("Now you have "+arrlist.size()+" tasks in the list");
    }


    /**
     * @Function
     * No Params, No Return Value
     * This function simply displays the task passed into it
     * @UsedIn: tasklist.taskDone,tasklist.deleteTask
     */
    public void showTask(Task t){
        System.out.println("[" + t.getSymbol() + "]" + "[" + t.getStatusIcon() + "] " + t.description);
    }

    /**
     * @Function
     * No Params, No Return Value
     * This function handles the list command which lists the tasks currently in Duke's tracking
     * It will display the task symbol (T,E,D), the status (done or not done) and the description string
     * @UsedIn: parser.processCommands
     */
    public void listTasks(){
        int count=1;
        System.out.println("Here are the tasks in your list:");
        for (Task t : arrlist) {
            System.out.print(count+++".");
            showTask(t);
        }
    }
}
