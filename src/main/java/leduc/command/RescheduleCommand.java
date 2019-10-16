package leduc.command;

import leduc.Date;
import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.Task;
import leduc.task.TaskList;

/**
 * Represents Reschedule command which reschedule the period of a event task.
 */
public class RescheduleCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String rescheduleShortcut = "reschedule";
    /**
     * Constructor of RescheduleCommand.
     * @param user String which represent the input string of the user.
     *
     */
    public RescheduleCommand(String user){
        super(user);
    }

    /**
     *
     * Allows to reschedule the period of a event task.
     *
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyEventDateException Exception caught when the period of the event task is not given by the user.
     * @throws NonExistentTaskException Exception caught when the task does not exist.
     * @throws EventTypeException Exception caught when the task is not a event task while it should be.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws DateComparisonEventException Exception caught when the second date is before the first one.
     * @throws FileException Exception caught when the file doesn't exist or cannot be created or cannot be opened.
     * @throws ConflictDateException Exception thrown when the new event is in conflict with others event.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EmptyEventDateException,
            NonExistentTaskException, EventTypeException, NonExistentDateException,
            DateComparisonEventException, FileException, ConflictDateException, EmptyArgumentException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(RescheduleCommand.rescheduleShortcut.length());
        }
        else {
            userSubstring = user.substring(10);
        }
        if(userSubstring.isBlank()){
            throw new EmptyArgumentException();
        }
        String[] rescheduleString = userSubstring.split("/at");
        if (rescheduleString.length == 1) { // no /by in input
            throw new EmptyEventDateException();
        }
        int index = -1;
        try {
            index = Integer.parseInt(rescheduleString[0].trim()) - 1;
        }
        catch(Exception e){
            throw new NonExistentTaskException();
        }
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // the tasks exist
            Task rescheduleTask = tasks.get(index);
            if (!rescheduleTask.isEvent()){
                throw new EventTypeException();
            }
            EventsTask rescheduleEventTask = (EventsTask) rescheduleTask;
            String[] dateString = rescheduleString[1].split(" - ");
            if(dateString.length == 1){
                throw new EmptyEventDateException();
            }
            else if(dateString[0].isBlank() || dateString[1].isBlank()){
                throw new EmptyEventDateException();
            }
            Date date1 = new Date(dateString[0]);
            Date date2 = new Date(dateString[1]);
            tasks.verifyConflictDate(date1, date2);
            rescheduleEventTask.reschedule(date1,date2);
            storage.save(tasks.getList());
            ui.display("\t Noted. I've rescheduled this task: \n" +
                    "\t\t "+rescheduleEventTask.getTag() + rescheduleEventTask.getMark() + " " +
                    rescheduleEventTask.getTask()+ " at:" + rescheduleEventTask.getDateFirst() +
                    " - " + rescheduleEventTask.getDateSecond() + "\n");
        }
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getRescheduleShortcut() {
        return rescheduleShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param rescheduleShortcut the new shortcut
     */
    public static void setRescheduleShortcut(String rescheduleShortcut) {
        RescheduleCommand.rescheduleShortcut = rescheduleShortcut;
    }
}
