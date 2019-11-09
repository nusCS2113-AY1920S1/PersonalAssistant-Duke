package moomoo.command;

import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;
import moomoo.stubs.ScheduleListStub;
import org.junit.jupiter.api.Test;
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

        assertEquals(" ____________________________________________________\n"
                + "/ Mooo.                                              \\\n"
                + "\\ New category named <food> added.                   /\n"
                + " ----------------------------------------------------\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n", Ui.returnResponse());


        command = new AddCategoryCommand("2271    CG");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" ____________________________________________________\n"
                + "/ Mooo.                                              \\\n"
                + "\\ New category named <2271    CG> added.             /\n"
                + " ----------------------------------------------------\n"
                + "        \\   ^__^\n"
                + "         \\  (oo)\\_______\n"
                + "            (__)\\       )\\/\\\n"
                + "                ||----w |\n"
                + "                ||     ||\n", Ui.returnResponse());
    }

    /*
    @Test
    void testDeleteCategoryCommand() throws MooMooException {
        ScheduleListStub calendar = new ScheduleListStub();
        Budget budget = new Budget();
        CategoryList categoryList = new CategoryList();
        Storage storage = new Storage();
        DeleteCategoryCommand command = new DeleteCategoryCommand("food");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" ____________________________________________________\n" +
                "/ Mooo.                                              \\\n" +
                "\\ New category named <food> added.                   /\n" +
                " ----------------------------------------------------\n" +
                "        \\   ^__^\n" +
                "         \\  (oo)\\_______\n" +
                "            (__)\\       )\\/\\\n" +
                "                ||----w |\n" +
                "                ||     ||\n", Ui.returnResponse());


        command = new DeleteCategoryCommand("2271    CG");
        command.execute(calendar, budget, categoryList, storage);

        assertEquals(" ____________________________________________________\n" +
                "/ Mooo.                                              \\\n" +
                "\\ New category named <2271    CG> added.             /\n" +
                " ----------------------------------------------------\n" +
                "        \\   ^__^\n" +
                "         \\  (oo)\\_______\n" +
                "            (__)\\       )\\/\\\n" +
                "                ||----w |\n" +
                "                ||     ||\n", Ui.returnResponse());
    }

     */
}

