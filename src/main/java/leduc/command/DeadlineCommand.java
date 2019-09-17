package leduc.command;

import leduc.Date;
import leduc.Parser;
import leduc.exception.DateFormatException;
import leduc.exception.EmptyDeadlineDateException;
import leduc.exception.EmptyDeadlineException;
import leduc.exception.NonExistentDateException;
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
     * @param parser leduc.Parser which deals with making sense of the user command.
     * @throws EmptyDeadlineDateException Exception caught when the date of the deadline task is not given.
     * @throws EmptyDeadlineException Exception caught when the description of the deadline task is not given.
     * @throws DateFormatException Exception caught when the date format is not correct.
     * @throws NonExistentDateException Exception caught when the date given does not exist.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser)
            throws EmptyDeadlineDateException, EmptyDeadlineException, DateFormatException, NonExistentDateException {
        String[] taskDescription = user.substring(8).split("/by");
        if (taskDescription[0].isBlank()) {
            throw new EmptyDeadlineException(ui);
        } else if (taskDescription.length == 1) { // no /by in input
            throw new EmptyDeadlineDateException(ui);
        } else {
            String description = taskDescription[0].trim();
            String deadlineString = taskDescription[1].trim();
            //date format used: dd/MM/yyyy HH:mm
            if (deadlineString.isBlank()) {
                throw new EmptyDeadlineDateException(ui);
            }
            else {
                LocalDateTime d1 = null;
                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                    d1 = LocalDateTime.parse(deadlineString.trim(), formatter);
                }catch(Exception e){
                    throw new NonExistentDateException(ui);
                }
                DeadlinesTask newTask = new DeadlinesTask(description, new Date(d1));
                tasks.add(newTask);
                try {
                    storage.getAppendWrite().write(tasks.size() + "//" + newTask.getTag() + "//" +
                            newTask.getMark() + "//" + newTask.getTask() + "//" + " by:"
                            + newTask.getDeadlines() + "\n");
                } catch (IOException e) {
                    ui.display("\t IOException:\n\t\t error when writing a deadline to file");
                }
                ui.display("\t Got it. I've added this task:\n\t   "
                        + newTask.getTag() + newTask.getMark() + newTask.getTask() + " by:"
                        + newTask.getDeadlines() +
                        "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    }

    /**
     * Returns a boolean false as it is a deadline command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
