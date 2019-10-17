package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.MooMooException;
import moomoo.task.Category;


public class TotalCommand extends Command {

    public TotalCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList catList, Category category, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, catList, category, ui, storage);
        catList.list(ui);
        ui.showEnterCategoryMessage();
        int cat = ui.readNumber() - 1;
        ui.showEnterMonthMessage();
        int month = ui.readNumber();
        double monthlyTotal = catList.get(cat).getMonthlyTotal(month);
        ui.showMonthlyTotal(monthlyTotal, catList.get(cat), month);
    }
}
