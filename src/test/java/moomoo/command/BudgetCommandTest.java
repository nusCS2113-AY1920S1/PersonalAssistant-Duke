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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BudgetCommandTest {
    @Test
    public void testSetBudgetCommand() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();

        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("shoes");
        categories.add("food");
        categories.add("places to go");
        categories.add("Test Value");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(50.0);
        budgets.add(100.0);
        budgets.add(150.0);
        budgets.add(200.0);

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub(budgetFile.getPath(), scheduleFile.getPath());
        Budget newBudget = new Budget();

        SetBudgetCommand setBudget = new SetBudgetCommand(false, categories, budgets);
        setBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have set $50.00 as the budget for shoes\n"
                + "You have set $100.00 as the budget for food\n"
                + "You have set $150.00 as the budget for places to go\n"
                + "Test Value category does not exist. Please add it first.\n", newUi.returnResponse());
    }

    @Test
    public void testEditBudgetCommand() throws MooMooException, IOException  {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("shoes");
        categories.add("food");
        categories.add("places to go");
        categories.add("Test Value");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(50.0);
        budgets.add(100.0);
        budgets.add(150.0);
        budgets.add(200.0);

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub(budgetFile.getPath(), scheduleFile.getPath());
        Budget newBudget = new Budget();

        SetBudgetCommand setBudget = new SetBudgetCommand(false, categories, budgets);
        setBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        budgets.set(0, 300.0);
        budgets.set(2, 675.0);

        EditBudgetCommand editBudget = new EditBudgetCommand(false, categories, budgets);
        editBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have changed the budget for shoes from $50.00 to $300.00\n"
                + "The budget for food is the same.\n"
                + "You have changed the budget for places to go from $150.00 to $675.00\n"
                + "Test Value category does not exist. Please add it first.\n", newUi.returnResponse());
    }

    @Test
    public void testListBudgetCommand() throws MooMooException, IOException  {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("shoes");
        categories.add("food");
        categories.add("places to go");
        categories.add("Test Value");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(50.0);
        budgets.add(100.0);
        budgets.add(150.0);
        budgets.add(200.0);

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub(budgetFile.getPath(), scheduleFile.getPath());

        Budget newBudget = new Budget();

        SetBudgetCommand setBudget = new SetBudgetCommand(false, categories, budgets);
        setBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        ArrayList<String> listCategories = new ArrayList<>();
        listCategories.add("shoes");
        listCategories.add("food");
        listCategories.add("places to go");
        listCategories.add("Test Value");
        listCategories.add("sweets");

        ListBudgetCommand listBudget = new ListBudgetCommand(false, listCategories);
        listBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("Budget for shoes is $50.00\n"
                + "Budget for food is $100.00\n"
                + "Budget for places to go is $150.00\nTest Value category does not exist. Please add it first.\n"
                + "Budget for sweets has not been set.\n", newUi.returnResponse());
    }

    @Test
    public void testSavingsBudgetCommand() throws MooMooException, IOException  {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("shoes");
        categories.add("food");
        categories.add("places to go");
        categories.add("Test Value");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(50.0);
        budgets.add(100.0);
        budgets.add(150.0);
        budgets.add(200.0);

        for (int i = 0; i < newCatList.getCategoryList().size(); ++i) {
            newCatList.getCategoryList().get(i).add(null);
        }

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub(budgetFile.getPath(), scheduleFile.getPath());
        Budget newBudget = new Budget();

        SetBudgetCommand setBudget = new SetBudgetCommand(false, categories, budgets);
        setBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        LocalDate startDate = LocalDate.of(2019, 9, 15);
        LocalDate endDate = LocalDate.of(2019, 11, 15);

        SavingsBudgetCommand savingsBudget = new SavingsBudgetCommand(false, categories, startDate, endDate);
        savingsBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have overspent for your budget for shoes from SEPTEMBER 2019 to NOVEMBER 2019 by: $115.00\n"
                + "Your savings for food from SEPTEMBER 2019 to NOVEMBER 2019 is: $35.00\n"
                + "Your savings for places to go from SEPTEMBER 2019 to NOVEMBER 2019 is: $185.00\n"
                + "test value category does not exist. Please create it first.\n"
                + "Your total savings: $105.00\n", newUi.returnResponse());

        savingsBudget = new SavingsBudgetCommand(false, categories, startDate, null);
        savingsBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have overspent your budget for shoes for SEPTEMBER 2019 by $100.00\n"
                + "You have overspent your budget for food for SEPTEMBER 2019 by $50.00\n"
                + "Your savings for places to go for SEPTEMBER 2019 is: $0\n"
                + "test value category does not exist. Please create it first.\n"
                + "You have overspent your total budget by: $150.00\n", newUi.returnResponse());

        categories.clear();
        assertEquals(0, categories.size());

        savingsBudget = new SavingsBudgetCommand(false, categories, startDate, null);
        savingsBudget.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        assertEquals("You have overspent your budget for shoes for SEPTEMBER 2019 by $100.00\n"
                + "You have overspent your budget for food for SEPTEMBER 2019 by $50.00\n"
                + "The budget for window does not exist. Please set it using budget set.\n"
                + "Your savings for places to go for SEPTEMBER 2019 is: $0\n"
                + "The budget for sweets does not exist. Please set it using budget set.\n"
                + "The budget for laptop does not exist. Please set it using budget set.\n"
                + "You have overspent your total budget by: $150.00\n", newUi.returnResponse());
    }
}