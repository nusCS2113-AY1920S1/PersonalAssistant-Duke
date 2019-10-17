package moomoo.command;

import moomoo.task.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.MooMooException;
import moomoo.task.Category;

import moomoo.task.Ui;
import moomoo.task.Storage;

public class AddCategoryCommand extends Command {

    public AddCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(calendar, budget, categoryList, category, ui, storage);

        categoryList.list(ui);
        ui.showAddCategoryMessage();
        String categoryName = ui.readCommand();
        Category newCategory = new Category(categoryName);
        categoryList.add(newCategory);
        ui.showNewCategoryMessage(categoryName);
    }
}
