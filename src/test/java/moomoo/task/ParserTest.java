package moomoo.task;

import moomoo.command.Command;
import moomoo.stubs.CategoryListStub;
import moomoo.stubs.CategoryStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import moomoo.stubs.UiStub;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void testParseSavingsBudgetCommand() {
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        ArrayList<String> categories = new ArrayList<>();

        categories.add("slippers");
        categories.add("window");
        categories.add("laptop");
        categories.add("Dogs");

        ArrayList<Double> budgets = new ArrayList<>();

        budgets.add(875.0);
        budgets.add(938.0);
        budgets.add(75.89);
        budgets.add(100.58);

        for (int i = 0; i < newCatList.getCategoryList().size(); ++i) {
            newCatList.getCategoryList().get(i).add(null);
        }

        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub();
        Budget newBudget = new Budget();
        Parser newParser = new Parser();

        try {
            Command c = newParser.parse("budget savings s/01/2019 e/14/2019", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        } catch (MooMooException e) {
            assertEquals("Please set an end month and year in this format \"e/01/2019\"\n", e.getMessage());
        }

        try {
            Command c = newParser.parse("budget savings s/13/2019 e/14/2019", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        } catch (MooMooException e) {
            assertEquals("Please set a start month and year in this format \"s/01/2019\"\n", e.getMessage());
        }

        try {
            Command c = newParser.parse("budget savings s/invalid input", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        } catch (MooMooException e) {
            assertEquals("Please input in this format \"c/CATEGORY "
                    + "s/STARTMONTHYEAR e/ENDMONTHYEAR\"\n", e.getMessage());
        }

        try {
            Command c = newParser.parse("budget savings s/invalid", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        } catch (MooMooException e) {
            assertEquals("Please set a start month and year in this format \"s/01/2019\"\n", e.getMessage());
        }
    }

    @Test
    public void testParseSetBudgetCommand() throws MooMooException {
        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub();
        Budget newBudget = new Budget();
        Parser newParser = new Parser();
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);

        Command c = newParser.parse("budget set c/food c/laptop b/123.45 c/places to go b/150", newUi);
        c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        assertEquals("You have set $123.45 as the budget for food\n"
                + "You have set $123.45 as the budget for laptop\n"
                + "You have set $150.00 as the budget for places to go\n", newUi.returnResponse());

        try {
            c = newParser.parse("budget set b/100 c/places to go b/150", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        } catch (MooMooException e) {
            assertEquals("Please input in this format \"c/CATEGORY b/BUDGET\"", e.getMessage());
        }
    }

    @Test
    public void testParseEditBudgetCommand() throws MooMooException {
        CategoryListStub newCatList = new CategoryListStub();
        newCatList.add(null);
        Budget newBudget = new Budget();
        Parser newParser = new Parser();
        CategoryStub newCategory = new CategoryStub();
        ScheduleListStub newCalendar = new ScheduleListStub();
        UiStub newUi = new UiStub();
        StorageStub newStorage = new StorageStub();

        Command c = newParser.parse("budget set c/food b/100 c/laptop b/125 c/places to go b/123", newUi);
        c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        c = newParser.parse("budget edit c/food c/laptop b/100 c/places to go b/150", newUi);
        c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);
        assertEquals("The budget for food is the same.\n"
            + "You have changed the budget for laptop from $125.00 to $100.00\n"
            + "You have changed the budget for places to go from $123.00 to $150.00\n", newUi.returnResponse());

        try {
            c = newParser.parse("budget edit b/100 c/places to go b/150", newUi);
            c.execute(newCalendar, newBudget, newCatList, newCategory, newUi, newStorage);

        } catch (MooMooException e) {
            assertEquals("Please input in this format \"c/CATEGORY b/BUDGET\"", e.getMessage());
        }
    }

}