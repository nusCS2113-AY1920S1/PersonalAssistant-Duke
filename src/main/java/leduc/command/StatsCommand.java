package leduc.command;
import leduc.UiEn;
import leduc.UiFr;
import leduc.exception.DukeException;
import leduc.exception.InvalidFlagException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import leduc.task.Task;

import java.util.Arrays;

/**
 * Represents the statistics feature
 */
public class StatsCommand extends Command {
    /**
     * general statistics that are generated from the tasklist.
     */
    private int numComplete = 0;
    private double numTasks = 0;
    private double percentComplete;
    private int numTodos = 0;
    private int numHomework = 0;
    private int numEvents = 0;
    private int numIncomplete = 0;
    /**
     * Priority statistics that are generated from the tasklist.
     */
    private int numFivePrio = 0;
    private int numFourPrio = 0;
    private int numThreePrio = 0;
    private int numTwoPrio = 0;
    private int numOnePrio = 0;
    private double percentFivePrio = 0.0;
    private double percentFourPrio = 0.0;
    private double percentThreePrio = 0.0;
    private double percentTwoPrio = 0.0;
    private double percentOnePrio = 0.0;
    /**
     * Completion statistics that are generated from the tasklist.
     */
    private int numIncompleteHomework = 0;
    private int numIncompleteTodo = 0;
    private int numIncompleteEvent = 0;
    private double percentIncompleteHomework = 0.0;
    private double percentIncompleteTodo = 0.0;
    private double percentIncompleteEvent = 0.0;
    /**
     * static variable used for shortcut
     */
    private static String StatsShortcut = "stats";
    public StatsCommand(String user){
        super(user);
    }
    /**
     * Analyze the tasklist and generate the "count" metrics
     * @param taskList leduc.task.TaskList which is the list of task.
     */
    void analyzeTaskList(TaskList taskList){
        numTasks = taskList.size();
        for (int i = 0 ;i< taskList.size() ; i++ ){
            Task task = taskList.get(i);
            if(task.getPriority() == 5){
                numFivePrio++;
            }
            if(task.getPriority() == 4){
                numFourPrio++;
            }
            if(task.getPriority() == 3){
                numThreePrio++;
            }
            if(task.getPriority() == 2){
                numTwoPrio++;
            }
            if(task.getPriority() == 1){
                numOnePrio++;
            }
            if(task.getMark().equals("[✓]")){
                numComplete++;
            }
            else{
                numIncomplete++;
                if(task.isHomework()){
                    numIncompleteHomework++;
                }
                if(task.isTodo()){
                    numIncompleteTodo++;
                }
                if(task.isEvent()){
                    numIncompleteEvent++;
                }
            }
            if(task.isHomework()){
                numHomework++;
            }
            else if(task.isEvent()){
                numEvents++;
            }
            else if(task.isTodo()){
                numTodos++;
            }
        }
    }
    /**
     * Uses the count metrics created from analyzeTaskList() to generate the percentage metrics
     */
    void createPercentageMetrics(){
        percentComplete = (numComplete/numTasks) * 100;
        percentIncompleteEvent = (double) numIncompleteEvent/numEvents * 100;
        percentIncompleteHomework = (double) numIncompleteHomework/numHomework * 100;
        percentIncompleteTodo = (double )numIncompleteTodo/numTodos * 100;
        percentFivePrio = (double) numFivePrio/numTasks * 100;
        percentFourPrio = (double) numFourPrio/numTasks * 100;
        percentThreePrio = (double) numThreePrio/numTasks * 100;
        percentTwoPrio = (double) numTwoPrio/numTasks * 100;
        percentOnePrio = (double) numOnePrio/numTasks * 100;
    }

    /**
     * If the user does not enter a flag, display the general statistics
     * @params ui to display the message string
     */
    void printGeneralStatistics(Ui ui){
        String message = "Here are some general statistics about your task list: \n" +
                "Number of tasks: " + numTasks + "\n" +
                "Number of Todo's : " + numTodos + "\n" +
                "Number of Events: " + numEvents + "\n" +
                "Number of Homeworks: " + numHomework + "\n" +
                "Number of Uncompleted Tasks: " + numIncomplete + "\n" +
                "Number of Completed Tasks: " + numComplete + "\n" +
                "Percent Complete: " + percentComplete + "%";
        ui.showFindMatching(message);
    }

    /**
     * If the user passes a "-p" flag, print detailed statistics about task priorities
     * @param ui to display message string
     */
    void printPriorityStatistics(Ui ui){
        String message = "Here are some priority statistics about your task list: \n" +
                "----PRIORITY COUNTS----" + "\n" +
                "Number of tasks with priority 5: " + numFivePrio + "\n" +
                "Number of tasks with priority 4: " + numFourPrio + "\n" +
                "Number of tasks with priority 3: " + numThreePrio + "\n" +
                "Number of tasks with priority 2: " + numTwoPrio + "\n" +
                "Number of tasks with priority 1: " + numOnePrio + "\n" +
                "----PRIORITY PERCENTAGES----" + "\n" +
                "Percent of tasks with priority 5: " + percentFivePrio + "%" + "\n" +
                "Percent of tasks with priority 4: " + percentFourPrio + "%" + "\n" +
                "Percent of tasks with priority 3: " + percentThreePrio + "%" + "\n" +
                "Percent of tasks with priority 2: " + percentTwoPrio + "%" + "\n" +
                "Percent of tasks with priority 1: " + percentOnePrio + "%";
        ui.display(message);
    }

    /**
     * If the user passes a "-c" flag, print detailed statistics about task completion
     * @param ui to display message string
     */
    void displayCompletionStatistics(Ui ui){
        String message = "Here are some completion statistics about your task list: \n" +
                "----COMPLETION COUNTS----" + "\n" +
                "Number of incomplete Homeworks remaining: " + numIncompleteHomework + "\n" +
                "Number of incomplete Todos remaining: " + numIncompleteTodo + "\n" +
                "Number of incomplete Events  remaining: " + numIncompleteEvent + "\n" +
                "----COMPLETION PERCENTAGES----" + "\n" +
                "Percent of incomplete Homework: " + percentIncompleteHomework + "%" + "\n" +
                "Percent of incomplete Todo: " + percentIncompleteTodo + "%" + "\n" +
                "Percent of incomplete Events: " + percentIncompleteEvent + "%";
        ui.display(message);
    }
    /**
     * Allow to see statistics on their taskList
     * @param taskList leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidFlagException {
        //get user flag
        String flag = String.join(" ", Arrays.copyOfRange(user.split(" "), 1, user.split( " ").length));
        analyzeTaskList(taskList);
        createPercentageMetrics();
        //display metrics

            if (flag.equals("")) {
                ui.showGeneralStats(numTasks, numTodos, numEvents, numHomework, numIncomplete, numComplete, percentComplete);
            }
            //display priority statistics
            else if (flag.equals("-p")) {
                ui.showPriorityStats(numFivePrio, numFourPrio,
                        numThreePrio, numTwoPrio, numOnePrio, percentFivePrio,
                        percentFourPrio, percentThreePrio, percentTwoPrio,
                        percentOnePrio);
            }
            //display completion statistics
            else if (flag.equals("-c")) {
                ui.showCompletionStats(numIncompleteHomework, numIncompleteTodo, numIncompleteEvent,
                        percentIncompleteHomework, percentIncompleteTodo, percentIncompleteEvent);
            } else {
                Exception e = new InvalidFlagException();
                ui.showError((DukeException) e);
            }


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
