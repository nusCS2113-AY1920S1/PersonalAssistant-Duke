package moomoo.command;

import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;

public class DeleteCategoryCommand extends Command {

    private String categoryName;

    public DeleteCategoryCommand(String categoryName) {
        super(false, "");
        this.categoryName = categoryName;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        try {
            storage.deleteCategoryFromFile(categoryName);
            categoryList.delete(categoryName);
            ui.showRemovedCategoryMessage(categoryName);
        } catch (IndexOutOfBoundsException e) {
            throw new MooMooException("Please enter a valid category number.");
        }
    }
}
