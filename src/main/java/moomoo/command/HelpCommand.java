package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;

public class HelpCommand extends Command {

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     */
    public HelpCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category, Ui ui,
                        Storage storage) {
        ui.showHelp();
    }
}
