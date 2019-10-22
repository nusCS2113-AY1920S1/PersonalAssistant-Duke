package moomoo.task;

import moomoo.command.BudgetCommand;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.CategoryStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.UiStub;
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

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();
        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        Storage newStorage = new Storage(budgetFile.getPath(), scheduleFile.getPath());

        newCatList.add(null);

        Budget newBudget = new Budget();
        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/sweets b/500 c/laptop b/1500");

        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        HashMap<String, Double> newHashMap = newStorage.loadBudget(newCatList.getCategoryList());
        assertEquals(500, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));

        budgetCommand = new BudgetCommand(false, "budget edit c/sweets b/700 c/laptop b/1500");
        budgetCommand.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        newHashMap = newStorage.loadBudget(newCatList.getCategoryList());
        assertEquals(700, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));
    }
}