package moomoo.task;

import moomoo.command.EditBudgetCommand;
import moomoo.command.SetBudgetCommand;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.CategoryStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.UiStub;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void testBudgetFileLoad() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("window");
        categories.add("sweets");
        categories.add("laptop");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(60.0);
        budgets.add(153.34);
        budgets.add(13840.45);

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        Storage newStorage = new Storage(budgetFile.getPath(), scheduleFile.getPath());
        Budget newBudget = new Budget();

        SetBudgetCommand setBudget = new SetBudgetCommand(false, categories, budgets);
        setBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        HashMap<String, Double> newHashMap = newStorage.loadBudget(newCatList.getCategoryList(), newUi);
        assertEquals(60.0, newHashMap.get("window"));
        assertEquals(153.34, newHashMap.get("sweets"));
        assertEquals(13840.45, newHashMap.get("laptop"));

        budgets.set(0, 500.23);
        budgets.set(2, 123.45);

        EditBudgetCommand editBudget = new EditBudgetCommand(false, categories, budgets);
        editBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        newHashMap = newStorage.loadBudget(newCatList.getCategoryList(), newUi);
        assertEquals(500.23, newHashMap.get("window"));
        assertEquals(153.34, newHashMap.get("sweets"));
        assertEquals(123.45, newHashMap.get("laptop"));
    }
}