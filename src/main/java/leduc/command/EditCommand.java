package leduc.command;

import leduc.Date;
import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.DeadlinesTask;
import leduc.task.EventsTask;
import leduc.task.Task;
import leduc.task.TaskList;


/**
 * Represents a EditCommand.
 * Allow to edit a task.
 */
public class EditCommand extends Command {

    /**
     * static variable used for shortcut
     */
    private static String editShortcut = "edit";
    /**
     * Constructor of EditCommand.
     * @param user String which represent the input string of the user.
     */
    public EditCommand(String user){
        super(user);
    }

    /**
     * Allow to edit a task.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify.
     * @throws NonExistentTaskException  Exception caught when the task to delete does not exist.
     * @throws MeaninglessException  Exception caught when the input string could not be interpreted.
     * @throws EmptyEventDateException Exception caught when one of the two date given does not exist.
     * @throws ConflictDateException Exception thrown when the new event is in conflict with others event.
     * @throws DateComparisonEventException  Exception caught when the second date is before the first one.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws NonExistentDateException, FileException,
            NonExistentTaskException, MeaninglessException, EmptyEventDateException, ConflictDateException,
            DateComparisonEventException {
        ui.display("\t Please choose the task to edit from the list by its index: ");
        ListCommand listCommand = new ListCommand(user);
        listCommand.execute(tasks,ui,storage);
        // The user choose the task
        String userEditTaskNumber = ui.readCommand();
        if ( userEditTaskNumber.matches("\\d+")){
            int index = Integer.parseInt(userEditTaskNumber.trim()) - 1;
            if (index > tasks.size() - 1 || index < 0) {
                throw new NonExistentTaskException();
            }
            else {
                Task t = tasks.get(index);
                if ( t.isTodo()){
                    ui.display("\t Please enter the new description of the todo Task");
                    t.setTask(ui.readCommand());
                }
                else{
                    ui.display("\t Please choose what you want to edit (1 or 2)\n\t 1. The description " +
                            "\n\t 2. The deadline/period");
                    String userEditTPart = ui.readCommand();
                    if ( userEditTPart.matches("\\d+")) {
                        int choice = Integer.parseInt(userEditTPart.trim());
                        if (choice == 1) {
                            ui.display("\t Please enter the new description of the task");
                            t.setTask(ui.readCommand());
                        } else if (choice == 2) {
                            if (t.isDeadline()) {
                                ui.display("\t Please enter the new deadline of the task");
                                String deadlineString = ui.readCommand();
                                Date d = new Date(deadlineString);
                                DeadlinesTask deadlinesTask = (DeadlinesTask) t;
                                deadlinesTask.setDeadlines(d);
                            } else { //event task
                                ui.display("\t Please enter the new period of the task");
                                String periodString = ui.readCommand();
                                String[] dateString = periodString.split(" - ");
                                if (dateString.length == 1) {
                                    throw new EmptyEventDateException();
                                } else if (dateString[0].isBlank() || dateString[1].isBlank()) {
                                    throw new EmptyEventDateException();
                                }
                                Date date1 = new Date(dateString[0]);
                                Date date2 = new Date(dateString[1]);
                                tasks.verifyConflictDate(date1, date2);
                                EventsTask eventsTask = (EventsTask) t;
                                eventsTask.reschedule(date1, date2);
                            }
                        } else {
                            throw new MeaninglessException();
                        }
                    }
                    else {
                        throw new MeaninglessException();
                    }
                }
                ui.display("\t The task is edited: \n\t "+ (index+1) + " " + t.toString());
            }
        }
        else {
            throw new MeaninglessException();
        }
        storage.save(tasks.getList());
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getEditShortcut() {
        return editShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param editShortcut the new shortcut
     */
    public static void setEditShortcut(String editShortcut) {
        EditCommand.editShortcut = editShortcut;
    }
}
