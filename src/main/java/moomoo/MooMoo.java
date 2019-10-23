package moomoo;

import moomoo.command.Command;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.Parser;
import moomoo.task.Category;

/**
 * Runs MooMoo.
 */
public class MooMoo {
    private Storage storage;
    private Category category;
    private CategoryList categoryList;
    private Budget budget;
    public ScheduleList calendar;
    private Ui ui;

    /**
     * Initializes different Category, Transaction Lists, Budget, Storage and Ui.
     */
    public MooMoo() {
        ui = new Ui();
        storage = new Storage("data/budget.txt","data/schedule.txt");

        categoryList = new CategoryList(storage.loadCategories());
        if (categoryList == null) {
            categoryList = new CategoryList();
        }

        budget = new Budget(storage.loadBudget(categoryList.getCategoryList(), ui));
        if (budget == null) {
            ui.returnResponse();
            budget = new Budget();
        }

        calendar = new ScheduleList(storage.loadCalendar(ui));
        if (calendar == null) {
            ui.returnResponse();
            calendar = new ScheduleList();
        }

    }

    /**
     * Runs the command line interface, reads input from user and returns result accordingly.
     */
    private void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand, ui);
                c.execute(calendar, budget, categoryList, category, ui, storage);

                if (!ui.returnResponse().equals("")) {
                    ui.showResponse();
                }
              
                isExit = c.isExit;
            } catch (MooMooException e) {
                ui.printException(e);
                ui.showResponse();
            }
        }
    }

    /**
     * Returns the response to the GUI when given an input by a user.
     * @param input Input given by user in the GUI
     * @return String Response to display on GUI by the bot.
     */
    public String getResponse(String input) {
        boolean isExit;
        try {
            Command c = Parser.parse(input, ui);
            c.execute(calendar, budget, categoryList, category, ui, storage);

            isExit = c.isExit;
            if (isExit) {
                ui.showGoodbye();
                return ui.returnResponse();
            }
            return ui.returnResponse();
        } catch (MooMooException e) {
            return ui.printException(e);
        }
    }

    /**
     * Runs MooMoo.
     * @param args Argument values given when running the program
     */
    public static void main(String[] args) {
        new MooMoo().run();
    }
}
