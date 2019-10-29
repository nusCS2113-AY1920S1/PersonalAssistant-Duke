package moomoo.command;

import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;

public class AddCategoryCommand extends Command {

    private String categoryName;

    /**
     * Command that takes in a category name from the Parser as string to be converted.
     * @param categoryName Category name to be added.
     */
    public AddCategoryCommand(String categoryName) {
        super(false, "");
        this.categoryName = categoryName;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        Category newCategory = new Category(categoryName.toLowerCase());
        if (categoryList.hasCategory(categoryName)) {
            throw new MooMooException("You already have a category named " + categoryName + ".");
        } else {
            categoryList.add(newCategory);
            storage.saveCategoryToFile(categoryName);
            ui.showNewCategoryMessage(categoryName);
        }
    }
}
