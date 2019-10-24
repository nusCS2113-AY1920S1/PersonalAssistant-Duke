package moomoo.command;

import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.Category;

public class ListCategoryCommand extends Command {
    public ListCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) {
        categoryList.list(ui);
    }
}
