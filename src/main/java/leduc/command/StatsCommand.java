/* @@author rshah918 */
package leduc.command;
import leduc.exception.InvalidFlagException;
import leduc.storage.Storage;
import leduc.ui.Ui;
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
    private int numNinePrio = 0;
    private int numEightPrio = 0;
    private int numSevenPrio = 0;
    private int numSixPrio = 0;
    private int numFivePrio = 0;
    private int numFourPrio = 0;
    private int numThreePrio = 0;
    private int numTwoPrio = 0;
    private int numOnePrio = 0;
    private double percentNinePrio = 0.0;
    private double percentEightPrio = 0.0;
    private double percentSevenPrio = 0.0;
    private double percentSixPrio = 0.0;
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

    /**
     * Contructor of StatsCommand
     * @param userInput The user input
     */
    public StatsCommand(String userInput){
        super(userInput);
    }
    /**
     * Analyze the tasklist and generate the "count" metrics
     * @param taskList leduc.task.TaskList which is the list of task.
     */
    void analyzeTaskList(TaskList taskList){
        numTasks = taskList.size();
        for (int i = 0 ;i< taskList.size() ; i++ ){
            Task task = taskList.get(i);
            int priority = task.getPriority();
            String completionMark = task.getMark();
            boolean isHomework = task.isHomework();
            boolean isEvent = task.isEvent();
            boolean isTodo = task.isTodo();
            //increment priority counts
            switch (priority) {
                case (9):
                    numNinePrio++;
                    break;

                case (8):
                    numEightPrio++;
                    break;

                case (7):
                    numSevenPrio++;
                    break;

                case (6):
                    numSixPrio++;
                    break;

                case (5):
                    numFivePrio++;
                    break;

                case (4):
                    numFourPrio++;
                    break;

                case (3):
                    numThreePrio++;
                    break;

                case (2):
                    numTwoPrio++;
                    break;

                case (1):
                    numOnePrio++;
                    break;
            }
            if(completionMark.equals("[V]")){
                numComplete++;
            }
            else{
                //increment incomplete task counts
                numIncomplete++;
                if(isHomework){
                    numIncompleteHomework++;
                }
                if(isTodo){
                    numIncompleteTodo++;
                }
                if(isEvent){
                    numIncompleteEvent++;
                }
            }
            //increment total task counts
            if(isHomework){
                numHomework++;
            }
            else if(isEvent){
                numEvents++;
            }
            else if(isTodo){
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
        percentNinePrio = (double) numNinePrio/numTasks * 100;
        percentEightPrio = (double) numEightPrio/numTasks * 100;
        percentSevenPrio = (double) numSevenPrio/numTasks * 100;
        percentSixPrio = (double) numSixPrio/numTasks * 100;
        percentFivePrio = (double) numFivePrio/numTasks * 100;
        percentFourPrio = (double) numFourPrio/numTasks * 100;
        percentThreePrio = (double) numThreePrio/numTasks * 100;
        percentTwoPrio = (double) numTwoPrio/numTasks * 100;
        percentOnePrio = (double) numOnePrio/numTasks * 100;
    }
    /**
     * Allow to see statistics on their taskList
     * @param taskList leduc.task.TaskList which is the list of task.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws InvalidFlagException {
        //get user flag
        String flag = String.join(" ", Arrays.copyOfRange(userInput.split(" "), 1, userInput.split( " ").length));
        analyzeTaskList(taskList);
        createPercentageMetrics();
        //display metrics
            if (flag.equals("")) {
                ui.showGeneralStats(numTasks, numTodos, numEvents, numHomework, numIncomplete, numComplete, percentComplete);
            }
            //display priority statistics
            else if (flag.equals("-p")) {
                ui.showPriorityStats(numNinePrio, numEightPrio, numSevenPrio, numSixPrio, numFivePrio, numFourPrio,
                        numThreePrio, numTwoPrio, numOnePrio,percentNinePrio, percentEightPrio, percentSevenPrio, percentSixPrio, percentFivePrio,
                        percentFourPrio, percentThreePrio, percentTwoPrio,
                        percentOnePrio);
            }
            //display completion statistics
            else if (flag.equals("-c")) {
                ui.showCompletionStats(numIncompleteHomework, numIncompleteTodo, numIncompleteEvent,
                        percentIncompleteHomework, percentIncompleteTodo, percentIncompleteEvent);
            } else {
                throw new InvalidFlagException();
            }
    }
    /* @@author */
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
