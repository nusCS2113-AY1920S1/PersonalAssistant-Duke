package moomoo.command;

import moomoo.task.category.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.category.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;


public class TotalCommand extends Command {

    public TotalCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) {
        catList.list(ui);
        ui.showEnterCategoryMessage();
        int cat = ui.readNumber() - 1;
        ui.showEnterMonthMessage();
        int month = ui.readNumber();
        double monthlyTotal = catList.get(cat).getTotal(month);
        ui.showMonthlyTotal(monthlyTotal, catList.get(cat), month);
    }
}
