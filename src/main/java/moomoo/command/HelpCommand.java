package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.category.CategoryList;

public class HelpCommand extends Command {

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     */
    public HelpCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) {
        String text = "Try one of these commands:\n"
                + "bye\n"
                + "list\n"
                + "category add [CATEGORY NAME]\n"
                + "category delete [CATEGORY NAME or CATEGORY INDEX NUMBER]\n"
                + "add n/[NAME] a/[AMOUNT SPENT] c/[CATEGORY] (optional: d/[YYYY-MM-DD])\n"
                + "delete i/[INDEX] c/[Category]\n"
                + "sort [TYPE]\n"
                + "budget\n"
                + "schedule\n"
                + "graph\n";
        Ui.setOutput(text);
    }
}
