package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.MainDisplay;

public class MainDisplayCommand extends Command {
    private int month;
    private int year;

    /**
     * Command that takes in a month and year from parser to be converted into int.
     * @param month Month that the user wants to view a summary of.
     * @param year Year that the user wants to view a summary of.
     */
    public MainDisplayCommand(int month, int year) {
        super(false, "");
        this.month = month;
        this.year = year;
    }

    /**
     * Executes a MainDisplay Command when called.
     * @param calendar ScheduleList object containing the schedules.
     * @param budget Budget object containing the budget.
     * @param categoryList CategoryList object containing the categories.
     * @param category Category object containing the expenditures
     * @param ui Ui object for interaction with user interface.
     * @param storage Storage object for interaction with filesystem.
     * @throws MooMooException Thrown when error such as invalid input occurs
     */
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        MainDisplay newMainDisplay = new MainDisplay();
        int rows;
        int cols;
        if (this.month != 0 && this.year != 0) {      // means user did specify a valid month and year
            rows = newMainDisplay.getMonthsExpSize(categoryList, month, year);
            cols = newMainDisplay.getCatListSize(categoryList);
        } else {
            cols = newMainDisplay.getCatListSize(categoryList);
            rows = newMainDisplay.getMaxCatSize(categoryList);
        }
        String output = newMainDisplay.newToPrint(month,year,rows,cols,categoryList,budget);
        ui.printMainDisplay(output);
    }
}
