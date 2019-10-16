package moomoo.command;

import moomoo.task.*;

public class DeleteCategoryCommand extends Command{

    public DeleteCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage) throws MooMooException {
        super.execute(budget, categoryList, transList, ui, storage);
        categoryList.list(ui);
        ui.showDeleteCategoryMessage();
        int categoryNumber = ui.readNumber() - 1;
        ui.showRemovedCategoryMessage(categoryList.get(categoryNumber));
        categoryList.deleteCategory(categoryNumber);
    }
}
