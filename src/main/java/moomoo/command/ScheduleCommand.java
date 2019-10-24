package moomoo.command;

import moomoo.task.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.SchedulePayment;

/**
 * Represents the command to create a scheduled payment event in advance.
 */
public class ScheduleCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public ScheduleCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        if (input.length() < 8) {
            throw new MooMooException("OOPS!!! To create a schedule payment, "
                    + "please indicate the d/<date in dd/mm/yyyy> a/<amount> t/<type of payment>.");
        }
        input = input.substring(8);
        if (!input.contains("d/")) {
            throw new MooMooException("OOPS!!! Please indicate the scheduled date using d/<dd/mm/yy>");
        } else if (!input.contains("a/")) {
            throw new MooMooException("OOPS!!! Please indicate the amount to be paid using a/<amount>");
        } else if (!input.contains("t/")) {
            throw new MooMooException("OOPS!!! Please indicate the type of payment using t/<type of payment>");
        }

        /*
        add scheduled tasks on a particular date to a list
         */
        String[] arr = input.split(" ", 4);
        String task;
        String date;
        task = arr[3];
        date = arr[1];
        date = date.replace("d/", "");
        String amount;
        amount = arr[2];
        task = task.replace("t/", "") + " " + amount.replace("a/", "");
        SchedulePayment newTask = new SchedulePayment(date, task);
        calendar.addToCalendar(newTask);
        ui.setOutput("You have scheduled a payment on " + date + " for " + task);
        storage.saveScheduleToFile(calendar);
    }
}
