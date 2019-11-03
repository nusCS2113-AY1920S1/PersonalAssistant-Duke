package moomoo.command.category;

import moomoo.command.Command;
import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.ScheduleList;
import moomoo.task.Ui;
import moomoo.task.Storage;

public class SortCategoryCommand extends Command {

    public SortCategoryCommand(String sortBy) {
        super(false, sortBy);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category, Ui ui,
                        Storage storage) {
        if (input.startsWith("by name")) {
            categoryList.sortByName();
        } else if (input.startsWith("by cost")) {
            categoryList.sortByValue();
        } else if (input.startsWith("by date")) {
            categoryList.sortByTime();
        }
    }
}
