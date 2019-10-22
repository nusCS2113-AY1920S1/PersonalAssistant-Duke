package moomoo.command;

import moomoo.stubs.CategoryListStub;
import moomoo.stubs.CategoryStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import moomoo.stubs.UiStub;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
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

        CategoryListStub newCatList = new CategoryListStub();
        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub(budgetFile.getPath(), scheduleFile.getPath());

        Budget newBudget = new Budget();

        newCatList.add(null);

        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/Shoes b/1000.79 c/food b/500");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for shoes is: $1000.79\nYour budget for food is: $500.00\n", newBudget.toString());
        assertEquals("You have set $1000.79 as the budget for shoes\n"
                + "You have set $500.00 as the budget for food\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget set c/places to go b/460");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $1000.79\n"
                + "Your budget for food is: $500.00\n", newBudget.toString());
        assertEquals("You have set $460.00 as the budget for places to go\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget edit c/shoes b/700 c/food b/400");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $700.00\n"
                + "Your budget for food is: $400.00\n", newBudget.toString());
        assertEquals("You have changed the budget for shoes from $1000.79 to $700.00\n"
                + "You have changed the budget for food from $500.00 to $400.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget edit c/shoes b/800 c/food b/400");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $800.00\n"
                + "Your budget for food is: $400.00\n", newBudget.toString());
        assertEquals("You have changed the budget for shoes from $700.00 to $800.00\n"
                + "The budget for food is the same.\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\nYour budget for shoes is: $800.00\n"
                + "Your budget for food is: $400.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list c/shoes");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for shoes is: $800.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list c/places to go");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for places to go is: $460.00\n", newUi.printResponse());

        for (int i = 0; i < newCatList.getCategoryList().size(); i++) {
            newCatList.get(i).add(null);
        }

        budgetCommand = new BudgetCommand(false, "budget savings s/10/2019 e/12/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your total savings from OCTOBER 2019 to DECEMBER 2019 is: $4635.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings s/01/2018");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your total savings for JANUARY 2018 is: $1360.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings c/Shoes c/food s/07/2019 e/09/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your savings for shoes from JULY 2019 to SEPTEMBER 2019 is: $2050.00\n"
                + "Your savings for food from JULY 2019 to SEPTEMBER 2019 is: $850.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget edit c/Shoes b/50");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have changed the budget for shoes from $800.00 to $50.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget list c/shoes");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Your budget for shoes is: $50.00\n", newUi.printResponse());

        budgetCommand = new BudgetCommand(false, "budget savings c/shoes s/09/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have overspent your budget for shoes for SEPTEMBER 2019 by $100.00\n", newUi.printResponse());

    }
}