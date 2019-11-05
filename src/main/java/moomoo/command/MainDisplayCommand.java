package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.category.CategoryList;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;
import moomoo.feature.MainDisplay;

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
     * @param storage Storage object for interaction with filesystem.
     */
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) {
        MainDisplay newMainDisplay = new MainDisplay();
        int rows = newMainDisplay.getCatListSize(categoryList);
        int cols = newMainDisplay.getMaxCatSize(categoryList);
        String output = newMainDisplay.toPrint(month,year,rows,cols,categoryList,budget);
        Ui.printMainDisplay(output);
    }
}
