package moomoo.command.category;

import moomoo.command.Command;
import moomoo.feature.Budget;
import moomoo.feature.category.CategoryList;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;

public class SortCategoryCommand extends Command {

    public SortCategoryCommand(String sortBy) {
        super(false, sortBy);
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Ui ui,
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
