package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.DeadlinesTask;
import leduc.task.TaskList;


/**
 * Represents a deadline task Command.
 * Allow to add a deadline task to the task list and to the data file.
 */
public class DeadlineCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String deadlineShortcut = "deadline";
    /**
     * Constructor of DeadlineCommand.
     * @param user String which represent the input string of the user.
     */
    public DeadlineCommand(String user){
        super(user);
    }

    /**
     * Allow to add a deadline task to the task list and to the data file.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyDeadlineDateException Exception caught when the date of the deadline task is not given.
     * @throws EmptyDeadlineException Exception caught when the description of the deadline task is not given.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws PrioritizeLimitException Exception caught when the new priority is greater than 9 or less than 0.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyDeadlineDateException, EmptyDeadlineException, NonExistentDateException,
            FileException, PrioritizeLimitException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(DeadlineCommand.deadlineShortcut.length());
        }
        else {
            userSubstring = user.substring(8);
        }
        if(userSubstring.isBlank()){
            throw new EmptyDeadlineException();
        }
        String[] taskDescription = userSubstring.split("/by");
        if (taskDescription[0].isBlank()) {
            throw new EmptyDeadlineException();
        } else if (taskDescription.length == 1) { // no /by in input
            throw new EmptyDeadlineDateException();
        } else {
            String description = taskDescription[0].trim();
            String[] prioritySplit = taskDescription[1].trim().split("prio");
            String deadlineString = prioritySplit[0].trim();
            //date format used: dd/MM/yyyy HH:mm
            if (deadlineString.isBlank()) {
                throw new EmptyDeadlineDateException();
            }
            else {
                Date d = new Date(deadlineString);
                DeadlinesTask newTask = null;
                if (prioritySplit.length == 1){
                    newTask = new DeadlinesTask(description, d);
                }
                else {
                    int priority = -1 ;
                    try{
                        priority = Integer.parseInt(prioritySplit[1].trim());
                    }
                    catch(Exception e){
                        throw new PrioritizeLimitException();
                    }
                    if (priority < 0 || priority > 9) {
                        throw new PrioritizeLimitException();
                    }
                    newTask = new DeadlinesTask(description,"[✗]",d,priority);
                }
                tasks.add(newTask);
                storage.save(tasks.getList());
                ui.display("\t Got it. I've added this task:\n\t   "
                        + newTask.toString() +
                        "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getDeadlineShortcut() {
        return deadlineShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param deadlineShortcut the new shortcut
     */
    public static void setDeadlineShortcut(String deadlineShortcut) {
        DeadlineCommand.deadlineShortcut = deadlineShortcut;
    }
}
