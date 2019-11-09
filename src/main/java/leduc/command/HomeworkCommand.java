package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.ui.Ui;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;


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
     * @param userInput String which represent the input string of the user.
     */
    public HomeworkCommand(String userInput){
        super(userInput);
    }

    /**
     * Allow to add a homework task to the task list and to the data file. The user can set a priority or a recurrence or both of them.
     * Recurrence only add new homework with day/week/month interval.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyHomeworkDateException Exception caught when the date of the homework task is not given.
     * @throws EmptyHomeworkException Exception caught when the description of the homework task is not given.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws PrioritizeLimitException Exception caught when the new priority is greater than 9 or less than 0.
     * @throws RecurrenceException Exception caught when the user doesn't respect the recurrence format
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyHomeworkDateException, EmptyHomeworkException, NonExistentDateException,
            FileException, PrioritizeLimitException, RecurrenceException {
        String userSubstring;
        int nbRecurrence = 0;
        String typeOfRecurrence = "";
        if(isCalledByShortcut){
            userSubstring = userInput.substring(HomeworkCommand.homeworkShortcut.length());
        }
        else {
            userSubstring = userInput.substring(8);
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
            String homeworkString = "";
            String description = taskDescription[0].trim();
            String[] prioritySplit = taskDescription[1].trim().split("prio");
            if(prioritySplit.length == 1){
                String[] recurrenceSplit = prioritySplit[0].trim().split(("recu"));
                homeworkString = recurrenceSplit[0].trim();
                if(!(recurrenceSplit.length==1)){
                    String[] recurrenceSplit2 = recurrenceSplit[1].trim().split(" ");
                    if(recurrenceSplit2.length == 1){
                        throw new RecurrenceException();
                    }
                    else {
                        typeOfRecurrence = recurrenceSplit2[0].trim();
                        if(!(typeOfRecurrence.equals("day") || typeOfRecurrence.equals("month") || typeOfRecurrence.equals("week"))){
                            throw new RecurrenceException();
                        }
                        try{
                            nbRecurrence = Integer.parseInt(recurrenceSplit2[1].trim());
                        }catch (Exception e){
                            throw new RecurrenceException();
                        }
                    }
                }
            }
            else {
                homeworkString = prioritySplit[0].trim();
            }
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
                    String[] recurrenceSplit = prioritySplit[1].trim().split(("recu"));
                    String priorityString = recurrenceSplit[0].trim();
                    if(!(recurrenceSplit.length==1)){
                        String[] recurrenceSplit2 = recurrenceSplit[1].trim().split(" ");
                        if(recurrenceSplit2.length == 1){
                            throw new RecurrenceException();
                        }
                        else {
                            typeOfRecurrence = recurrenceSplit2[0].trim();
                            if(!(typeOfRecurrence.equals("day") || typeOfRecurrence.equals("month") || typeOfRecurrence.equals("week"))){
                                throw new RecurrenceException();
                            }
                            try{
                                nbRecurrence = Integer.parseInt(recurrenceSplit2[1].trim());
                            }catch (Exception e){
                                throw new RecurrenceException();
                            }
                        }
                    }
                    try{
                        priority = Integer.parseInt(priorityString);
                    }
                    catch(Exception e){
                        throw new PrioritizeLimitException();
                    }
                    if (priority < 0 || priority > 9) {
                        throw new PrioritizeLimitException();
                    }
                    newTask = new HomeworkTask(description,d,priority);
                }
                if(nbRecurrence == 0){
                    tasks.add(newTask);
                    storage.save(tasks.getList());
                    ui.showTask(newTask, tasks.size());
                }
                else {
                    contructRecurrenceTask(newTask, nbRecurrence, typeOfRecurrence, tasks, storage, ui);
                }

            }
        }
    }
    /**
     * Helper method to construct recurrence task
     * @param task the task that will be repeated
     * @param nbRecurrence the number of recurrence
     * @param typeOfRecurrence type of recurrence, can be day, week or month
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    private void contructRecurrenceTask(HomeworkTask task, int nbRecurrence, String typeOfRecurrence, TaskList tasks, Storage storage, Ui ui) throws FileException {
        ArrayList<Task> newTaskList = new ArrayList<>();
        LocalDateTime initialDate = task.getDeadlines().getDate();
        String description = task.getTask();
        int priority = task.getPriority();
        newTaskList.add(task);
        tasks.add(task);
        switch (typeOfRecurrence){
            case "day":
                for(int i = 1; i<= nbRecurrence; i++){
                    HomeworkTask recurrentHomeworkTask = new HomeworkTask(description, new Date(initialDate.plusDays(i)), priority);
                    newTaskList.add(recurrentHomeworkTask);
                    tasks.add(recurrentHomeworkTask);
                }
                ui.showNotCompleteList(newTaskList, tasks);
                break;
            case "week":
                for(int i = 1; i<= nbRecurrence; i++){
                    HomeworkTask recurrentHomeworkTask = new HomeworkTask(description, new Date(initialDate.plusWeeks(i)), priority);
                    newTaskList.add(recurrentHomeworkTask);
                    tasks.add(recurrentHomeworkTask);
                }
                ui.showNotCompleteList(newTaskList, tasks);
                break;
            case "month":
                for(int i = 1; i<= nbRecurrence; i++){
                    HomeworkTask recurrentHomeworkTask = new HomeworkTask(description, new Date(initialDate.plusMinutes(i)), priority);
                    newTaskList.add(recurrentHomeworkTask);
                    tasks.add(recurrentHomeworkTask);
                }
                ui.showNotCompleteList(newTaskList, tasks);
                break;
            default:
                tasks.add(task);
                ui.showTask(task, tasks.size());
                break;
        }
        storage.save(tasks.getList());
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
