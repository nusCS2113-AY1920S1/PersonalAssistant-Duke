package duke.command;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;


import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Class representing a command to view tasks based on specific date.
 */
public class ViewCommand extends Command {

    private final LocalDate date;
    private static final SimpleDateFormat inputFormatter = new SimpleDateFormat("d/M/yyyy");
    private static final SimpleDateFormat displayFormatter = new SimpleDateFormat("EEEE dd MMMM yyyy");
    private final String input;

    /**
     * Creates a new viewCommand
     * @param date tasks to be viewed on this date
     * @throws DukeException when invalid date input format of DD/MM/YYYY
     */
    public ViewCommand(String date) throws DukeException {
        if (date.isBlank() || date.isEmpty()) {
            throw new DukeException("The time cannot be empty or space bar");
        }

        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        if (!date.trim().matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }

        try {
            String[] format_date = date.trim().split("/");
            int day = Integer.parseInt(format_date[0]);
            int month = Integer.parseInt(format_date[1]);
            int year = Integer.parseInt(format_date[2]);
            this.date = LocalDate.of(year, month, day);

            //format for printing
            Date ddate = inputFormatter.parse(date);
            this.input = displayFormatter.format(ddate);
        } catch (DateTimeParseException | ParseException e) {
            throw new DukeException("Time must be in the format DD/MM/YYYY format");
        }
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> result = tasks.vfilter(date);
        if (result.size() == 0) {
            ui.printError("There are no tasks for " + input);
        } else {
            ui.printMessage("Here are the matching tasks in your list for " + input + ":");
            for (int i = 0; i < result.size(); i++) {
                ui.printMessage(i + 1 + "." + result.get(i).toString());
            }
        }

    }
}
