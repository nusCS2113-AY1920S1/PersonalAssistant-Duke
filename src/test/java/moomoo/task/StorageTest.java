package moomoo.task;

import moomoo.command.BudgetCommand;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void testFileLoad() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File transactionsFile = File.createTempFile("transactions", ".txt");
        transactionsFile.deleteOnExit();

        File categoriesFile = File.createTempFile("categories", ".txt");
        categoriesFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryList newCatList = new CategoryList();
        newCatList.getCategoryList().add(new Category("sweets"));
        newCatList.getCategoryList().add(new Category("laptop"));
        newCatList.getCategoryList().add(new Category("desktop"));

        Budget newBudget = new Budget();
        Ui newUi = new Ui();
        Category newCategory = new Category();
        Storage newStorage = new Storage(budgetFile.getPath(), transactionsFile.getPath(), categoriesFile.getPath(),
                scheduleFile.getPath());

        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/sweets b/500 c/laptop b/1500");
        ScheduleList newCalendar = new ScheduleList();
        SchedulePayment a = new SchedulePayment("30/11/2019", "pay electricity bills");
        newCalendar.fullSchedule.add(a);
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        HashMap<String, Double> newHashMap = newStorage.loadBudget(newCatList);
        assertEquals(500, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));

        budgetCommand = new BudgetCommand(false, "budget edit c/sweets b/700 c/laptop b/1500");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        newHashMap = newStorage.loadBudget(newCatList);
        assertEquals(700, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));
    }
}