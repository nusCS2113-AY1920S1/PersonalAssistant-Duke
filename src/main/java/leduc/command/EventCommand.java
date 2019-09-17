package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.EventsTask;
import leduc.task.TaskList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a event task Command.
 * Allow to add a event task to the task list and to the data file.
 */
public class EventCommand extends Command {
    /**
     * Constructor of EventCommand.
     * @param user String which represent the input string of the user.
     */
    public  EventCommand(String user){
        super(user);
    }

    /**
     * Allow to add a event task to the task list and to the data file.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyEventDateException Exception caught when the period of the event task is not given by the user.
     * @throws EmptyEventException Exception caught when the description of the event task is not given by the user.
     * @throws NonExistentDateException Exception caught when one of the two date given does not exist.
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyEventDateException, EmptyEventException, NonExistentDateException, FileException {
        String[] taskDescription = user.substring(5).split("/at");
        if (taskDescription[0].isBlank()) {
            throw new EmptyEventException();
        }
        else if (taskDescription.length == 1) { // no /at in input
            throw new EmptyEventDateException();
        }
        else {
            String description = taskDescription[0].trim();
            String periodString = taskDescription[1].trim();
            //date format used: dd/MM/yyyy HH:mm - dd/MM/yyyy HH:mm
            String[] dateString = periodString.split(" - ");
            if(dateString.length == 1){
                throw new EmptyEventDateException();
            }
            else if(dateString[0].isBlank() || dateString[1].isBlank()){
                throw new EmptyEventDateException();
            }
            LocalDateTime d1 = null;
            LocalDateTime d2 = null;
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                d1 = LocalDateTime.parse(dateString[0].trim(), formatter);
                d2 = LocalDateTime.parse(dateString[1].trim(), formatter);
            }catch(Exception e){
                throw new NonExistentDateException();
            }
            EventsTask newTask = new EventsTask(description, new Date(d1) , new Date(d2));
            tasks.add(newTask);
            storage.save(tasks.getList());
            ui.display("\t Got it. I've added this task:\n\t   "
                    + newTask.toString() +
                    "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
}
