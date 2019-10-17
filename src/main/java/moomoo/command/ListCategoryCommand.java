package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.TransactionList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.MooMooException;

public class ListCategoryCommand extends Command {
    public ListCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, categoryList, transList, ui, storage);
        categoryList.list(ui);
    }
}
