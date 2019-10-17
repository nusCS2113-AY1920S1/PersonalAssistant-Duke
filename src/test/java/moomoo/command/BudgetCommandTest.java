package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.ScheduleList;
import moomoo.task.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCommandTest {
    @Test
    public void testBudgetSubCommands() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryList newCatList = new CategoryList();
        newCatList.getCategoryList().add(new Category("shoes"));
        newCatList.getCategoryList().add(new Category("food"));
        newCatList.getCategoryList().add(new Category("window"));
        newCatList.getCategoryList().add(new Category("places to go"));

        Category newCategory = new Category();
        Budget newBudget = new Budget();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(budgetFile.getPath(), scheduleFile.getPath());

        ScheduleList newCalendar = new ScheduleList();
        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/shoes b/1000.79 c/food b/500");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for shoes is: $1000.79\nYour budget for food is: $500.00\n", newBudget.toString());
        assertEquals("You have set $1000.79 as the budget for shoes\n"
                + "You have set $500.00 as the budget for food", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget set c/places to go b/460");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $1000.79\n"
                + "Your budget for food is: $500.00\n", newBudget.toString());
        assertEquals("You have set $460.00 as the budget for places to go", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget edit c/shoes b/700 c/food b/400");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $700.00\n"
                + "Your budget for food is: $400.00\n", newBudget.toString());
        assertEquals("You have changed the budget for shoes from $1000.79 to $700.00\n"
                + "You have changed the budget for food from $500.00 to $400.00", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget edit c/shoes b/800 c/food b/400");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $800.00\n"
                + "Your budget for food is: $400.00\n", newBudget.toString());
        assertEquals("You have changed the budget for shoes from $700.00 to $800.00\n"
                + "The budget for food is the same.", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $800.00\n"
                + "Your budget for food is: $400.00", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list c/shoes");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for shoes is: $800.00", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list c/places to go");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings s/10/2019 e/12/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your total savings from OCTOBER 2019 to DECEMBER 2019 is: ", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings s/01/2018");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your total savings for JANUARY 2018 is: ", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings c/shoes c/food s/07/2019 e/09/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your savings for shoes from JULY 2019 to SEPTEMBER 2019 is: $800.00\n"
                + "Your savings for food from JULY 2019 to SEPTEMBER 2019 is: $400.00", newUi.printResponse());
    }
}