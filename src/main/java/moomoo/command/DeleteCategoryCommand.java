package moomoo.command;

import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;

public class DeleteCategoryCommand extends Command {

    private int categoryNumber;

    public DeleteCategoryCommand(int categoryNumber) {
        super(false, "");
        this.categoryNumber = categoryNumber;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        ui.showRemovedCategoryMessage(categoryList.get(categoryNumber));
        storage.deleteCategoryFromFile(categoryList.get(categoryNumber).toString());
        categoryList.delete(categoryNumber);
    }
}
