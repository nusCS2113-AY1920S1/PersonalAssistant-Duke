/**
 * Represents the user interface which displays the messages to
 * respond to the user based on the user's input.
 */
public class Ui {
    protected String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    protected String welcomeMessage = "Hello! I'm Duke\n"
            + "What can I do for you?\n";
    protected String byeMessage = "Bye. Hope to see you again soon!\n";
    protected String line = "____________________________________________________________\n";

    /**
     * Displays the welcome message when Duke program starts.
     * @return This returns a message to greet the user
     */
    public String showWelcome(){
        return "Hello from\n" + logo + welcomeMessage;
    }

    /**
     * Displays the exit message when Duke Program ends.
     * @return This returns a message to say goodbye to the user
     */
    public String showBye(){
        return byeMessage;
    }

    /**
     * Displays the list message when user inputs list.
     * @param list The TaskList object to display all the tasks
     * @return This returns the list message and all contents in the TaskList object
     */
    public String showList(TaskList list){
        String listMessage = "Here are the tasks in your list:\n";
        for (int i = 1; i <= list.taskListSize(); i++) {
            listMessage = listMessage + i + "." + list.taskToString(i-1) + "\n";
        }
        return listMessage;
    }

    /**
     * Displays add task message when user wants to add a task.
     * @param task The task to be added by the user
     * @param listSize The size of the ArrayList after adding a task
     * @return This returns the add task message with the current size of teh ArrayList
     */
    public String showAdd(Task task, int listSize){
        return "Got it. I've added this task:\n" + task.toString() + "\n"
                + "Now you have " + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays done task message when user marks a task as done.
     * @param task The task that user wants to mark done
     * @return This returns the done message with the task that is marked done
     */
    public String showDone(Task task){
        return "Nice! I've marked this task as done:\n" + task.toString() + "\n";
    }

    /**
     * Displays the delete task message when user wants to delete a task.
     * @param task The task that user wants to delete
     * @param listSize The size of the ArrayList after a task is deleted
     * @return This returns the delete message with the task and size of ArrayList after deletion
     */
    public String showDelete(Task task, int listSize){
        return "Noted. I've removed this task:\n" + task.toString() + "\n" + "Now you have "
                + listSize + (listSize > 1 ? " tasks in the list.\n" : " task in the list.\n");
    }

    /**
     * Displays the find message when a user wants to find tasks with a specific keyword.
     * @param list The TaskList object with contents that contain the keyword
     * @return This either returns a message indicating no content or a message with contents in the TaskList object
     */
    public String showFind(TaskList list){
        if(list.taskListSize() == 0) {
            return "There are no matching tasks in your list\n";
        }
        else {
            String findMessage = "Here are the matching tasks in your list:\n";
            for (int i = 1; i <= list.taskListSize(); i++) {
                findMessage = findMessage + i + "." + list.taskToString(i - 1) + "\n";
            }
            return findMessage;
        }
    }

    /**
     * Displays the error message if a file is not found.
     * @param e Exception that was caught
     * @return This returns the Exception error message
     */
    public String showLoadingError(Exception e){
        return "File not found" + e.getMessage() + "\n";
    }

    /**
     * Displays any of the DukeException error message caught throughout the program.
     * @param e DukeException that was caught
     * @return This returns the DukeException error message
     */
    public String showError(DukeException e){
        return e.getMessage() + "\n";
    }
}
