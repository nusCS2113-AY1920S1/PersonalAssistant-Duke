package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.MooMooException;
import moomoo.task.Category;
import moomoo.task.Ui;

public class AddCategoryCommand extends Command {

    public AddCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, Category category, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, category, ui, storage);

        categoryList.list(ui);
        ui.showAddCategoryMessage();
        String categoryName = ui.readCommand();
        Category newCategory = new Category(categoryName);
        categoryList.add(newCategory);
        ui.showNewCategoryMessage(categoryName);
    }
}
