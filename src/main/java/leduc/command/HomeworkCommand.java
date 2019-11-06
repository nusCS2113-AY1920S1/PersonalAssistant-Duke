package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.HomeworkTask;
import leduc.task.TaskList;


/**
 * Represents a homework task Command.
 * Allow to add a homework task to the task list and to the data file.
 */
public class HomeworkCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String homeworkShortcut = "homework";
    /**
     * Constructor of HomeworkCommand.
     * @param user String which represent the input string of the user.
     */
    public HomeworkCommand(String user){
        super(user);
    }

    /**
     * Allow to add a homework task to the task list and to the data file.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyHomeworkDateException Exception caught when the date of the homework task is not given.
     * @throws EmptyHomeworkException Exception caught when the description of the homework task is not given.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws PrioritizeLimitException Exception caught when the new priority is greater than 9 or less than 0.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyHomeworkDateException, EmptyHomeworkException, NonExistentDateException,
            FileException, PrioritizeLimitException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(HomeworkCommand.homeworkShortcut.length());
        }
        else {
            userSubstring = user.substring(8);
        }
        if(userSubstring.isBlank()){
            throw new EmptyHomeworkException();
        }
        String[] taskDescription = userSubstring.split("/by");
        if (taskDescription[0].isBlank()) {
            throw new EmptyHomeworkException();
        } else if (taskDescription.length == 1) { // no /by in input
            throw new EmptyHomeworkDateException();
        } else {
            String description = taskDescription[0].trim();
            String[] prioritySplit = taskDescription[1].trim().split("prio");
            String homeworkString = prioritySplit[0].trim();
            //date format used: dd/MM/yyyy HH:mm
            if (homeworkString.isBlank()) {
                throw new EmptyHomeworkDateException();
            }
            else {
                Date d = new Date(homeworkString);
                HomeworkTask newTask = null;
                if (prioritySplit.length == 1){
                    newTask = new HomeworkTask(description, d);
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
                    newTask = new HomeworkTask(description,d,priority);
                }
                tasks.add(newTask);
                storage.save(tasks.getList());
                ui.showTask(newTask, tasks.size());
            }
        }
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getHomeworkShortcut() {
        return homeworkShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param homeworkShortcut the new shortcut
     */
    public static void setHomeworkShortcut(String homeworkShortcut) {
        HomeworkCommand.homeworkShortcut = homeworkShortcut;
    }
}
