package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.MooMooException;
import moomoo.task.Category;
import moomoo.task.Ui;

public class DeleteCategoryCommand extends Command {

    public DeleteCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, transList, ui, storage);

        categoryList.list(ui);
        ui.showEnterCategoryMessage();
        int categoryNumber = ui.readNumber() - 1;
        ui.showRemovedCategoryMessage(categoryList.get(categoryNumber));
        categoryList.deleteCategory(categoryNumber);
    }
}
