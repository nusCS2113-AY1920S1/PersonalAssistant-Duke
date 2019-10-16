package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.MooMooException;
import moomoo.task.Category;
import moomoo.task.Ui;

public class AddCategoryCommand extends Command {

    private String categoryName;

    public AddCategoryCommand(String categoryName) {
        super(false, "");
        this.categoryName = categoryName;
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, transList, ui, storage);

        Category newCategory = new Category(categoryName);
        categoryList.add(newCategory);
        ui.showNewCategoryMessage(categoryName);
    }
}
