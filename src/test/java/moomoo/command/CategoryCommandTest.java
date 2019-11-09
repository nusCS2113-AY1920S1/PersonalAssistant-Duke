package moomoo.command;

import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.command.category.DeleteExpenditureCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.Storage;
import moomoo.stubs.ScheduleListStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryCommandTest {
    @Test
    void testAddCategoryCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new Storage();
        AddCategoryCommand command = new AddCategoryCommand("food");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" __________________________________________________\n"
                + "/ Mooo.                                            \\\n"
                + "\\ Added category named : food                      /\n"
                + " --------------------------------------------------\n", Ui.getTestOutput());


        command = new AddCategoryCommand("2271    CG");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" __________________________________________________\n"
                + "/ Mooo.                                            \\\n"
                + "\\ Added category named : 2271    CG                /\n"
                + " --------------------------------------------------\n", Ui.getTestOutput());
    }

    /*
    @Test
    void testDeleteCategoryCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new Storage();

        categoryList.add(new Category("food"));
        categoryList.add(new Category("2271    CG"));
        DeleteCategoryCommand command = new DeleteCategoryCommand("food");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" __________________________________________________\n"
                + "/ Mooo.                                            \\\n"
                + "\\ Re-MOOO-ved category named : food                /\n"
                + " --------------------------------------------------\n", Ui.getTestOutput());


        command = new DeleteCategoryCommand("2271    CG");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" __________________________________________________\n"
                + "/ Mooo.                                            \\\n"
                + "\\ Re-MOOO-ved category named : 2271    CG          /\n"
                + " --------------------------------------------------\n", Ui.getTestOutput());
    }

     */

    @Test
    void testAddExpenditureCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new Storage();
        categoryList.add(new Category("food"));
        AddExpenditureCommand command = new AddExpenditureCommand("banana", 8.50,
                LocalDate.now(), "food");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals("  __________________________________________________\n"
                + " / Mooo.                                            \\\n"
                + "|  Expenditure named : banana                        |\n"
                + " \\ Added to category : food                         /\n"
                + "  --------------------------------------------------\n", Ui.getTestOutput());
    }

    /*
    @Test
    void testDeleteExpenditureCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new Storage();

        categoryList.add(new Category("2271    CG"));
        categoryList.get("2271    CG").add(new Expenditure("module", 99.9, LocalDate.now(),
                "2271    CG"));
        DeleteExpenditureCommand command = new DeleteExpenditureCommand(0, "2271    CG");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals("  __________________________________________________\n"
                + " / Mooo.                                            \\\n"
                + "|  Expenditure named : module                        |\n"
                + " \\ Deleted from category : 2271    CG               /\n"
                + "  --------------------------------------------------\n", Ui.getTestOutput());
    }

     */
}

