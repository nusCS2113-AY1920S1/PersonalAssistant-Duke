package moomoo.command;

import moomoo.task.category.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.category.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.util.ArrayList;

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
                    + "please indicate the d/<date in dd/mm/yyyy> a/<amount> n/<description of payment>.");
        }
        input = input.substring(8);
        if (!input.contains("d/")) {
            throw new MooMooException("MOOOO!!! Please indicate the scheduled date using d/<dd/mm/yy>");
        } else if (!input.contains("a/")) {
            throw new MooMooException("MOOOO!!! Please indicate the amount to be paid using a/<amount>");
        } else if (!input.contains("n/")) {
            throw new MooMooException("MOOOO!!! Please indicate the type of payment using n/<description of payment>");
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
        task = task.replace("n/", "") + " " + amount.replace("a/", "");
        if (calendar.calendar.containsKey(date)) {
            ArrayList<String> dayTasks = calendar.calendar.get(date);
            dayTasks.add(task);
            calendar.calendar.replace(date, dayTasks);
        } else {
            calendar.addToCalendar(date, task);
        }
        String blankSpace = " ";
        int blank = 31 - date.length();
        for (int j = 1; j <= blank; j++) {
            blankSpace += " ";
        }
        String blank2 = " ";
        blank = 23 - task.length();
        for (int j = 0; j < blank; j++) {
            blank2 += " ";
        }
        String cow =
                "._______________________________________.\n"
                + "| ___  ___ _  _ ___ ___  _   _ _    ___ |\n"
                + "|/ __|/ __| || | __|   \\| | | | |  | __||\n"
                + "|\\__ \\ (__| __ | _|| |) | |_| | |__| _| |\n"
                + "||___/\\___|_||_|___|___/ \\___/|____|___||\n"
                + "|                                       |\n"
                + "|Date : " + date + blankSpace + "|\n"
                + "|Task : " + task + " dollars" + blank2 + "|\n"
                + ".---------------------------------------.\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n";
        ui.setOutput(cow);
        storage.saveScheduleToFile(calendar);
    }
}
