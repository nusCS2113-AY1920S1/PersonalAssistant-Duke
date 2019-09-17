package leduc.command;

import leduc.Date;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.DeadlinesTask;
import leduc.task.TaskList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a deadline task Command.
 * Allow to add a deadline task to the task list and to the data file.
 */
public class DeadlineCommand extends Command {
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
     * @throws FileException Exception caught when the file can't be open or read or modify
     */
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws EmptyDeadlineDateException, EmptyDeadlineException, NonExistentDateException, FileException {
        String[] taskDescription = user.substring(8).split("/by");
        if (taskDescription[0].isBlank()) {
            throw new EmptyDeadlineException();
        } else if (taskDescription.length == 1) { // no /by in input
            throw new EmptyDeadlineDateException();
        } else {
            String description = taskDescription[0].trim();
            String deadlineString = taskDescription[1].trim();
            //date format used: dd/MM/yyyy HH:mm
            if (deadlineString.isBlank()) {
                throw new EmptyDeadlineDateException();
            }
            else {
                LocalDateTime d1 = null;
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                    d1 = LocalDateTime.parse(deadlineString.trim(), formatter);
                }catch(Exception e){
                    throw new NonExistentDateException();
                }
                DeadlinesTask newTask = new DeadlinesTask(description, new Date(d1));
                tasks.add(newTask);
                storage.save(tasks.getList());
                ui.display("\t Got it. I've added this task:\n\t   "
                        + newTask.toString() +
                        "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    }

}
