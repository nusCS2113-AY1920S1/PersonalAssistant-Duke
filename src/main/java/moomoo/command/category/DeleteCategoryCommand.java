package moomoo.command.category;

import moomoo.command.Command;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.ScheduleList;

public class DeleteCategoryCommand extends Command {

    public DeleteCategoryCommand(String categoryName) {
        super(false, categoryName);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        try {
            storage.deleteCategoryFromFile(input);
            categoryList.delete(input);
            ui.showRemovedCategoryMessage(input);
        } catch (IndexOutOfBoundsException e) {
            throw new MooMooException("Please enter a valid category name.");
        }
    }
}
