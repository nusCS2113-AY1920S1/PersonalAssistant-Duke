package moomoo.command.category;

import moomoo.command.Command;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.CategoryStorage;
import moomoo.feature.storage.ExpenditureStorage;
import moomoo.feature.storage.Storage;

public class DeleteCategoryCommand extends Command {

    private int categoryIndex;
    private boolean isString;

    /**
     * Initializes a command to delete a category.
     * @param categoryName category to be deleted
     */
    public DeleteCategoryCommand(String categoryName) {
        super(false, categoryName);
        this.categoryIndex = 0;
        this.isString = true;
    }

    /**
     * Initializes a command to delete a category.
     * @param categoryName category to be deleted
     */
    public DeleteCategoryCommand(int categoryName) {
        super(false, "");
        this.categoryIndex = categoryName;
        this.isString = false;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {

        if (isString) {
            this.categoryIndex = categoryList.find(input);
        } else {
            this.input = categoryList.get(categoryIndex).name();
        }
        try {
            CategoryStorage.deleteFromFile(input);
            categoryList.delete(categoryIndex);
            Ui.showCategoryMessage("Re-MOOO-ved category named : " + input);
        } catch (IndexOutOfBoundsException e) {
            throw new MooMooException("Please enter a valid category name.");
        }
    }
}
