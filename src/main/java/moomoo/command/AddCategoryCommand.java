package moomoo.command;

import moomoo.task.*;

public class AddCategoryCommand extends Command {

    public AddCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(calendar, budget, categoryList, transList, ui, storage);

        categoryList.list(ui);
        ui.showAddCategoryMessage();
        String categoryName = ui.readCommand();
        Category newCategory = new Category(categoryName);
        categoryList.add(newCategory);
        ui.showNewCategoryMessage(categoryName);
    }
}
