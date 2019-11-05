package moomoo.command.category;

import moomoo.command.Command;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.Storage;

public class AddCategoryCommand extends Command {

    /**
     * Command that takes in a category name from the Parser as string to be converted.
     * @param categoryName Category name to be added.
     */
    public AddCategoryCommand(String categoryName) {
        super(false, categoryName);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {

        Category newCategory = new Category(input.toLowerCase());
        if (categoryList.hasCategory(input)) {
            throw new MooMooException("You already have a category named " + input + ".");
        } else if (input.contains("|")) {
            throw new MooMooException("Please do not use | in your category name");
        } else {
            categoryList.add(newCategory);
            CategoryStorage.saveToFile(input);
            Ui.showCategoryMessage("New category named " + input + " added. ");
        }
    }
}
