package moomoo.command.category;

import moomoo.command.Command;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.category.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.category.Category;

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
