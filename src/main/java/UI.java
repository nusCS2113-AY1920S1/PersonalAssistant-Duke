import java.util.ArrayList;

/**
 * User interface: contains all methods pertaining to user interaction.
 */
public class UI {
    private static String lineSeparation = "____________________________________________________________\n";

    /**
     * prints welcome message & instructions for use.
     */
    public void welcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(lineSeparation + "Hello! I'm Duke\nWhat can i do for you?\n");

        System.out.println("Commands:");
        System.out.println("1. list: Print a list of tasks currently stored.");
        System.out.println("2. todo <description of task>: Adds a simple task with no time or date involved");
        System.out.println("3. event OR deadline <description of task> /at OR /by <time>: adds an event/deadline to the list of tasks.");
        System.out.println("4. done <task number>: completes a task");
        System.out.println("5. bye: exits the program\n");
        System.out.println("When entering dates and times, you may do so in the following format for faster entry : \n" +
                "<day>/<month>/<year> <time(24hr format)>\n" + lineSeparation);
        System.out.println("Enter a command:");
    }

    /**
     * Prints a message when an invalid command is entered.
     */
    public void printInvalidCommand(){
        System.out.print(lineSeparation);
        System.out.println("Sorry! I don't know what that means.");
        System.out.print(lineSeparation);
    }

    /**
     * prints entire list of tasks stored.
     * @param tasks TaskList object containing all stored classes and pertaining methods.
     */
    public static void printListOfTasks(TaskList tasks){
        System.out.print(lineSeparation);
        System.out.print(tasks.listOfTasks_String());
        System.out.print(lineSeparation);
    }

    /**
     * prints goodbye message
     */
    public static void bye(){
        System.out.print(lineSeparation + "Bye. Hope to see you again soon!\n" + lineSeparation);
    }

    /**
     * @return line of underscores to separate different UI outputs.
     */
    public String getLineSeparation(){
        return lineSeparation;
    }

    /**
     * prints message when a task is successfully added
     * @param taskAdded task in question
     * @param numTasks total number of tasks
     */
    public void taskAdded(Task taskAdded, int numTasks){
        System.out.println(lineSeparation + "Got it. I've added this task:");
        System.out.println(taskAdded.toString());
        System.out.println("Now you have " + numTasks + " tasks in the list.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when a task is marked as completed
     * @param task task in question
     */
    public void taskDone(Task task) {
        System.out.print(lineSeparation);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message when a task is deleted successfully
     * @param task task in question to be deleted
     */
    public void taskDeleted(Task task) {
        System.out.print(lineSeparation);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message containing tasks found when a search is performed.
     * prints error message if no tasks are found
     * @param allFoundTasks string containing all the tasks found, separated by newline character
     * @param found boolean signifying whether or not any tasks were found
     */
    public void searchTasks(String allFoundTasks, boolean found) {
        if (found) {
            System.out.print(lineSeparation);
            System.out.println("Here are the matching tasks in your list:");
            System.out.print(allFoundTasks);
            System.out.print(lineSeparation);
        } else {
            System.out.print(lineSeparation);
            System.out.println("No such tasks were found! Please try again.");
            System.out.print(lineSeparation);
        }
    }

    /**
     * prints message if command does not contain valid input for related task.
     */
    public void noSuchTask(){
        System.out.print(lineSeparation);
        System.out.println("There is no such task! Please try again.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message if no task description is found when adding a new task to the list
     */
    public void taskDescriptionEmpty(){
        System.out.print(lineSeparation);
        System.out.println("The description of your task cannot be empty!");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when task index from input is not an integer
     */
    public void notAnInteger(){
        System.out.print(lineSeparation);
        System.out.println("That is not an integer! Please enter the index of the task you intend to alter.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when format of input is wrong for adding new deadline
     */
    public void deadlineFormatWrong(){
        System.out.print(lineSeparation);
        System.out.println("Please enter the name of the task followed by the deadline, separated by /by");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when input format is wrong for addition of new event type task.
     */
    public void eventFormatWrong(){
        System.out.print(lineSeparation);
        System.out.println("Please enter the name of the event and its date/time, separated by /at");
        System.out.print(lineSeparation);
    }
}
