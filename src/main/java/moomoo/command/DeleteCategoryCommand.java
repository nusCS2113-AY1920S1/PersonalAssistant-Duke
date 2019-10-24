package moomoo.command;

import moomoo.task.*;

public class DeleteCategoryCommand extends Command {

    public DeleteCategoryCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, Category category,
                        Ui ui, Storage storage) throws MooMooException {

        categoryList.list(ui);
        ui.showEnterCategoryMessage();
        int categoryNumber = ui.readNumber() - 1;
        ui.showRemovedCategoryMessage(categoryList.get(categoryNumber));
        storage.deleteCategoryFromFile(categoryList.get(categoryNumber).toString());
        categoryList.delete(categoryNumber);
    }
}
