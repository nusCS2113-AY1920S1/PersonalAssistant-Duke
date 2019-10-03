package owlmoney.logic.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import owlmoney.logic.exception.DukeException;
import owlmoney.storage.Storage;
import owlmoney.model.task.Task;
import owlmoney.model.task.TaskList;
import owlmoney.ui.Ui;

/**
 * Class representing a command to view tasks based on specific date.
 */
public class ViewCommand extends Command {

    private final LocalDate date;
    private static final SimpleDateFormat inputFormatter = new SimpleDateFormat("d/M/yyyy");
    private static final SimpleDateFormat displayFormatter = new SimpleDateFormat("EEEE dd MMMM yyyy");
    private final String input;

    /**
     * Creates a new ViewCommand.
     *
     * @param date tasks to be viewed on this date
     * @throws DukeException when invalid date input format of DD/MM/YYYY
     */
    public ViewCommand(String date) throws DukeException {
        if (date.isBlank() || date.isEmpty()) {
            throw new DukeException("The time cannot be empty or space bar");
        }

        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        if (!date.trim().matches(dateRegex)) {
            throw new DukeException("The date format is wrong/invalid, please try in DD/MM/YYYY format");
        }

        checkValidDate(date, "d/M/yyyy"); //check for invalid date

        try {
            String[] formatDate = date.trim().split("/");
            int day = Integer.parseInt(formatDate[0]);
            int month = Integer.parseInt(formatDate[1]);
            int year = Integer.parseInt(formatDate[2]);
            this.date = LocalDate.of(year, month, day);

            //format for printing
            Date tempDate = inputFormatter.parse(date);
            this.input = displayFormatter.format(tempDate);
        } catch (DateTimeParseException | ParseException e) {
            throw new DukeException("Date must be in the format DD/MM/YYYY format");
        }
    }

    /**
     * Checks for invalid date input.
     *
     * @param validateDate is the date input from user
     * @param dateFormat   proper date format
     * @return returns true if date is valid
     * @throws DukeException throws an exception when non-existent date is provided
     */
    public boolean checkValidDate(String validateDate, String dateFormat) throws DukeException {
        SimpleDateFormat checkDate = new SimpleDateFormat(dateFormat);
        checkDate.setLenient(false);

        try {
            checkDate.parse(validateDate);
        } catch (ParseException e) {
            throw new DukeException("This date doesn't exist in the calendar!");
        }
        return true;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks   The task list.
     * @param ui      The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> result = tasks.viewFilterByDate(date);
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
