package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.MooMooException;
import moomoo.task.Category;

public class ListCategoryCommand extends Command {
    public ListCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, Category category, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, category, ui, storage);
        categoryList.list(ui);
    }
}
