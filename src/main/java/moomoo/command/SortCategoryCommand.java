package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.MooMooException;

public class SortCategoryCommand extends Command {

    public SortCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category, Ui ui,
                        Storage storage) throws MooMooException {

    }
}
