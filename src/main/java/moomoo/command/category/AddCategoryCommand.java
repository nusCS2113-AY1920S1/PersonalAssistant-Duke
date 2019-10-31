package moomoo.command.category;

import moomoo.command.Command;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.ScheduleList;

public class AddCategoryCommand extends Command {

    /**
     * Command that takes in a category name from the Parser as string to be converted.
     * @param categoryName Category name to be added.
     */
    public AddCategoryCommand(String categoryName) {
        super(false, categoryName);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        Category newCategory = new Category(input.toLowerCase());
        if (categoryList.hasCategory(input)) {
            throw new MooMooException("You already have a category named " + input + ".");
        } else {
            categoryList.add(newCategory);
            storage.saveCategoryToFile(input);
            ui.showNewCategoryMessage(input);
        }
    }
}
