package leduc;

import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *  Represents leduc.Ui which deals with the interactions with the user.
 */
public abstract class Ui {
    private Scanner sc;

    /**
     * Constructor of the leduc.Ui
     */
    public Ui(){
        this.sc = new Scanner(System.in);
    }

    /**
     * Returns the String representing the next line of command of the user.
     * @return the String representing the next line of command of the user.
     */
    public String readCommand(){
        return this.sc.nextLine();
    }


    /**
     * Display the duke logo.
     */
    public void displayLogo(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
    }

    /**
     * Show welcome to the user.
     */
    public void showWelcome(Storage storage) throws FileException {

        //open the file
        File file = storage.getWelcomeFile();

        //create Scanner object to read file
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }

        String welcomeMessage = "";
        //build welcome message
        while (sc2.hasNext()) {
            welcomeMessage = welcomeMessage + '\t' + sc2.nextLine() + '\n';
        }
        //display welcome message
        this.displayLogo();
        this.display(welcomeMessage);
    }


    /**
     * Bye message to the user.
     */

    public abstract void showBye();

    /**
     * Display this message when a task has been delete
     * @param removedTask the task that was removed
     * @param size the size of the TaskList after deletion
     */
    public abstract void showDelete(Task removedTask, int size);

    /**
     * Display this message when a task has been done
     * @param doneTask the task that has been done
     */
    public abstract void showDone(Task doneTask);

    /**
     * Display this message when a new task has been added
     * @param newTask the new task that has been added
     * @param size the size of the TaskList after the addition
     */

    public abstract void showTask(Task newTask, int size);

    /**
     * Display this message when there is a matching for the find
     * @param result the list of all matching task
     */
    public abstract void showFindMatching(String result);

    /**
     * Display this message when there is no matching for the find
     */
    public abstract void showFindNotMatching();
    /**
     * Display this message when there is no task to display
     */
    public abstract void showNoTask();

    /**
     * Display this message when a task has been postponed
     * @param postponeTask the task that has been postponed
     */
    public abstract void showPostpone(HomeworkTask postponeTask);

    /**
     * Display this message when there is a task where the priority has been set
     * @param task the task where the priority has been set
     */
    public abstract void showPrioritize(Task task);

    /**
     * Display this message when a task has been rescheduled
     * @param rescheduleTask the task that has been rescheduled
     */
    public abstract void showReschedule(EventsTask rescheduleTask);

    /**
     * Display this message when a new welcome message has been set
     * @param welcomeMessage the new welcome message
     */
    public abstract void showNewWelcome(String welcomeMessage);

    /**
     * Display this message when a task has been snoozed
     * @param snoozeTask
     */
    public abstract void showSnooze(HomeworkTask snoozeTask);

    /**
     * Display this message when the list of tasks has been sorted
     */
    public abstract void showSort();

    /**
     * Display this message when the user wants some general statistics
     * @param numTasks number of tasks
     * @param numTodos number of todos
     * @param numEvents number of events
     * @param numHomework number of homework
     * @param numIncomplete number of uncompleted task
     * @param numComplete number of completed task
     * @param percentComplete percent complete
     */
    public abstract void showGeneralStats(double numTasks, double numTodos, double numEvents, double numHomework,
                                   double numIncomplete, double numComplete, double percentComplete);


    /**
     * Dispplays priority statistics
     * @param numNinePrio = number of 9 priority tasks
     * @param numEightPrio = number of 8 priority tasks
     * @param numSevenPrio = number of 7 priority tasks
     * @param numSixPrio = number of 6 priority tasks
     * @param numFivePrio = number of 5 priority tasks
     * @param numFourPrio = number of 4 priority tasks
     * @param numThreePrio = number of 3 priority tasks
     * @param numTwoPrio = number of 2 priority tasks
     * @param numOnePrio = number of 1 priority tasks
     * @param percentNinePrio = % of 9 priority tasks
     * @param percentEightPrio = % of 8 priority tasks
     * @param percentSevenPrio = % of 7 priority tasks
     * @param percentSixPrio = % of 6 priority tasks
     * @param percentFivePrio = % of 5 priority tasks
     * @param percentFourPrio = % of 4 priority tasks
     * @param percentThreePrio = % of 3 priority tasks
     * @param percentTwoPrio = % of 2 priority tasks
     * @param percentOnePrio = % of 1 priority tasks
     */
    public abstract void showPriorityStats(int numNinePrio, int numEightPrio, int numSevenPrio, int numSixPrio, int numFivePrio, int numFourPrio, int numThreePrio, int numTwoPrio, int numOnePrio, double percentNinePrio, double percentEightPrio,double percentSevenPrio, double percentSixPrio, double percentFivePrio, double percentFourPrio, double percentThreePrio, double percentTwoPrio, double percentOnePrio);

    /**
     * Displays completion statistics
     * @param numIncompleteHomework = number of incomplete homeworks
     * @param numIncompleteTodo = number of incomplete Todo's
     * @param numIncompleteEvent = number of incomplete events
     * @param percentIncompleteHomework = % of incomplete homeworks
     * @param percentIncompleteTodo = % of incomplete Todo's
     * @param percentIncompleteEvent = % of incomplete Events
     */
    public abstract void showCompletionStats( int numIncompleteHomework,
             int numIncompleteTodo,
             int numIncompleteEvent,
             double percentIncompleteHomework,
             double percentIncompleteTodo,
             double percentIncompleteEvent);

    /**
     * Display this message when a new language has been set for the program
     * @param lang the new language
     */
    public abstract void showLanguage(String lang);
    /**
     * Display the String in the parameter between two lines.
     * @param s String which will be printed.
     */
    public void display(String s){
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println(s);
        System.out.println("\t---------------------------------------------------------------------------------");
    }
    /**
     * Display the unfinished tasks to the terminal
     * @param unfinishedTasks the arraylist of unfinished tasks.
     */
    public abstract void showUnFinishedTasks(ArrayList<Task> unfinishedTasks);
    /**
     * Display the list of tasks.
     * @param tasks tasks list.
     */
    public abstract void showList(TaskList tasks);


    /**
     * display when the program want to show a partial list with their right index
     * @param notCompleteTasks the partial list
     * @param tasks the complete list that will be compare to
     */
    public abstract void showNotCompleteList(ArrayList<Task> notCompleteTasks, TaskList tasks);//print the task so they have the same index



    /**
     * Display the error message
     * @param e the error that has been catch
     */
    public abstract void showError(DukeException e);

    /**
     * Display every command
     */
    public abstract void showHelp();

    /**
     * display when the program ask the user which task he want to edit
     */
    public abstract void showEditChooseTask();

    /**
     * display when the program ask the user to choose between 2 choices : description or deadline/period
     */
    public abstract void showEdit2Choice();

    /**
     * display when the program ask the user to enter a new information for the choice
     * @param choice the choice : description, deadline or period
     */
    public abstract void showEditWhat(String choice);

    /**
     * display the task that has been edited
     * @param task the task that has been edited
     */
    public abstract void showEdit(Task task);

    /**
     * display when the program ask the user to set a shortcut name for the command name
     * @param commandName the command name that will have a new shortcut name
     */
    public abstract void showAskShortcut(String commandName);

    /**
     * display when the program ask the user to set a shortcut name for the command name
     * @param commmandName command name
     * @param shortcutName the previous shortcut name
     */
    public abstract void showAskAllShortcut(String commmandName, String shortcutName);

    /**
     * display after setting a shortcut name for the command name
     * @param commandName the command name which the shortcut name has been set
     */
    public abstract void showOneShortcutSet(String commandName);

    /**
     * display when the shortcut for all the command has been set
     */
    public abstract void showAllShortcutSet();
    /**
     * display when the program ask the user to enter a day date
     */
    public abstract void showEnterDayShow();

    /**
     * display when the program ask the user to enter a day of week
     */
    public abstract void showEnterDayOfWeekShow();
    /**
     * display when the program ask the user to enter a month
     */
    public abstract void showEnterMonthShow();
    /**
     * display when the program ask the user to enter a year
     */
    public abstract void showEnterYearShow();

    /**
     * display when the edit shortcut mode terminate earlier
     */
    public abstract void terminateShortcut();

    /**
     * display when the user want to show to another language rathan than those available
     */
    public abstract void showErrorLanguage();
}
