package moomoo.command;

import moomoo.task.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCommandTest {
    @Test
    public void testBudgetSubCommands() throws MooMooException, IOException {
        File tempFile = File.createTempFile("moomoo", ".txt");
        tempFile.deleteOnExit();

        ScheduleList newCalendar = new ScheduleList();
        TransactionList newTransList = new TransactionList();
        CategoryList newCatList = new CategoryList();
        Budget newBudget = new Budget();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set d/1000.79");

        budgetCommand.execute(newCalendar, newBudget, newCatList, newTransList, newUi, newStorage);

        assertEquals("Your budget is: $1000.79", newBudget.toString());

        ByteArrayInputStream in = new ByteArrayInputStream("y".getBytes()); //send in user input
        System.setIn(in);

        budgetCommand = new BudgetCommand(false, "budget edit d/500.50");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newTransList, newUi, newStorage);

        System.setIn(System.in);

        assertEquals("You have set your new budget to be $500.50 every month.", newUi.printToGui());

        budgetCommand = new BudgetCommand(false, "budget list");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newTransList, newUi, newStorage);

        assertEquals("Your budget is: $500.50", newUi.printToGui());

        budgetCommand = new BudgetCommand(false, "budget savings s/12/2019");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newTransList, newUi, newStorage);
        assertEquals("Your savings for DECEMBER 2019 is: ", newUi.printToGui());

    }
}