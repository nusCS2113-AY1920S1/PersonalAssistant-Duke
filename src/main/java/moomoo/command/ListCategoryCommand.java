package moomoo.command;

import moomoo.task.*;

public class ListCategoryCommand extends Command {
    public ListCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        super.execute(calendar, budget, categoryList, transList, ui, storage);
        categoryList.list(ui);
    }
}
