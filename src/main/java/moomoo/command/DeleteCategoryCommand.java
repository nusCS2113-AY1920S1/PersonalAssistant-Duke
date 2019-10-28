package moomoo.command;

import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;

public class DeleteCategoryCommand extends Command {

    public DeleteCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        categoryList.list(ui);
        ui.showEnterCategoryMessage();
        int categoryNumber = ui.readNumber() - 1;
        ui.showRemovedCategoryMessage(categoryList.get(categoryNumber));
        storage.deleteCategoryFromFile(categoryList.get(categoryNumber).toString());
        categoryList.delete(categoryNumber);
    }
}
