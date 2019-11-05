package moomoo;

import moomoo.command.Command;
import moomoo.task.Budget;
import moomoo.task.category.Expenditure;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.Parser;
import moomoo.task.ScheduleList;
import moomoo.task.SchedulePayment;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Runs MooMoo.
 */
public class MooMoo {
    private Storage storage;
    private Category category;
    private CategoryList categoryList;
    private Budget budget;
    private Expenditure expenditure;
    public ScheduleList calendar;
    private Ui ui;

    /**
     * Initializes different Category, Expenditures, Budget, Storage and Ui.
     */
    public MooMoo() {
        ui = new Ui();
        storage = new Storage("data/budget.txt","data/schedule.txt",
                "data/category.txt", "data/expenditure.txt");
        try {
            categoryList = storage.loadExpenditure();
        } catch (MooMooException e) {
            ui.printException(e);
            ui.showResponse();
            categoryList = new CategoryList();
        }

        /*
        try {
            category = new Category(storage.loadExpenditures());
        } catch (MooMooException e) {
            ui.printExceptions(e);
            ui.showResponse();
            category = new Category();
        } */

        HashMap<String, Double> loadedBudget = storage.loadBudget(categoryList.getCategoryList(), ui);
        if (loadedBudget == null) {
            ui.showResponse();
            budget = new Budget();
        } else {
            budget = new Budget(loadedBudget);
        }

        ArrayList<SchedulePayment> scheduleList = storage.loadCalendar(ui);
        if (scheduleList == null) {
            ui.showResponse();
            calendar = new ScheduleList();
        } else {
            calendar = new ScheduleList(scheduleList);
        }
        /*
        ArrayList<Expenditure> category = storage.loadExpenditures(ui);
        if (category == null) {
            ui.showResponse();
            expenditure = new Expenditure();
        } else {
            expenditure = new Expenditure(category);
        } */
    }

    /**
     * Runs the command line interface, reads input from user and returns result accordingly.
     */
    private void run() {
        ui.showWelcome();
        String date = ui.showDate();
        String todaySchedule = calendar.showSchedule(date);
        ui.setOutput(todaySchedule);
        ui.showResponse();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                System.out.print("\u001b[2J");
                System.out.flush();
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
