package moomoo.command;

import moomoo.task.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;

public class AddCategoryCommand extends Command {

    private String categoryName;

    public AddCategoryCommand(String categoryName) {
        super(false, "");
        this.categoryName =categoryName;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        super.execute(calendar, budget, categoryList, category, ui, storage);

        Category newCategory = new Category(categoryName);
        categoryList.add(newCategory);
        storage.saveCategoryToFile(categoryName);
        ui.showNewCategoryMessage(categoryName);
    }
}
