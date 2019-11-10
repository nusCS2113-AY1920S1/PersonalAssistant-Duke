package moomoo.command;

import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.command.category.DeleteExpenditureCommand;
import moomoo.command.category.ListCategoryCommand;
import moomoo.command.category.SortCategoryCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryCommandTest {
    @Test
    void testAddCategoryCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new StorageStub();
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

    @Test
    void testDeleteCategoryCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new CategoryStorage();

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

    @Test
    void testAddExpenditureCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new StorageStub();
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

    @Test
    void testDeleteExpenditureCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new ExpenditureStorage();

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

    @Test
    void testListCommand() throws MooMooException {

        CategoryList categoryList = new CategoryList();
        categoryList.add(new Category("food"));
        categoryList.add(new Category("shoes"));
        categoryList.add(new Category("places to go"));
        categoryList.add(new Category("test value"));

        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        Storage storage = new StorageStub();
        ListCategoryCommand command = new ListCategoryCommand();
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(".__________________________________________________.\n"
                + "|Here are your current categories.                 |\n"
                + "|1. food [ $0.0 ]                                  |\n"
                + "|2. shoes [ $0.0 ]                                 |\n"
                + "|3. places to go [ $0.0 ]                          |\n"
                + "|4. test value [ $0.0 ]                            |\n"
                + ".--------------------------------------------------.\n", Ui.getTestOutput());
    }

    @Test
    void testSortCommand() throws MooMooException {

        Category category = new Category("food");
        LocalDate date = LocalDate.parse("2019-11-10");
        category.add(new Expenditure("grape", 62, date, "food"));
        category.add(new Expenditure("banana", 40, date, "food"));
        category.add(new Expenditure("fig", 45, date, "food"));
        category.add(new Expenditure("durian", 20, date, "food"));
        category.add(new Expenditure("clementine", 80, date, "food"));
        category.add(new Expenditure("apple", 50, date, "food"));

        CategoryList categoryList = new CategoryList();
        categoryList.add(category);

        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        Storage storage = new StorageStub();
        SortCategoryCommand command = new SortCategoryCommand("name");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals("food | apple | 50.0 | 2019-11-10\n"
                + "food | banana | 40.0 | 2019-11-10\n"
                + "food | clementine | 80.0 | 2019-11-10\n"
                + "food | durian | 20.0 | 2019-11-10\n"
                + "food | fig | 45.0 | 2019-11-10\n"
                + "food | grape | 62.0 | 2019-11-10\n", Ui.getTestOutput());

        command = new SortCategoryCommand("cost");
        command.execute(calendar, budget, categoryList, storage);
        assertEquals("food | clementine | 80.0 | 2019-11-10\n"
                + "food | grape | 62.0 | 2019-11-10\n"
                + "food | apple | 50.0 | 2019-11-10\n"
                + "food | fig | 45.0 | 2019-11-10\n"
                + "food | banana | 40.0 | 2019-11-10\n"
                + "food | durian | 20.0 | 2019-11-10\n", Ui.getTestOutput());
    }
}

