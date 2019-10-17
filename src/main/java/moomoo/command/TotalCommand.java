package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.CategoryList;
import moomoo.task.TransactionList;
import moomoo.task.Ui;
import moomoo.task.Storage;
import moomoo.task.MooMooException;


public class TotalCommand extends Command {

    public TotalCommand() {
        super(false, "");
    }

    @Override
    public void execute(Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(budget, catList, transList, ui, storage);
        catList.list(ui);
        ui.showEnterCategoryMessage();
        int category = ui.readNumber() - 1;
        ui.showEnterMonthMessage();
        int month = ui.readNumber();
        double monthlyTotal = catList.get(category).getMonthlyTotal(month);
        ui.showMonthlyTotal(monthlyTotal, catList.get(category), month);
    }
}
