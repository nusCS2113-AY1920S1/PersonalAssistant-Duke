package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.category.CategoryList;

public class HelpCommand extends Command {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     */
    public HelpCommand() {
        super(false, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) {
        String text = ANSI_GREEN + "Try one of these commands:\n\n" + ANSI_RESET
                + "1.  list\n"
                + "2.  category add [CATEGORY NAME]\n"
                + "3.  category delete [CATEGORY NAME or CATEGORY INDEX NUMBER]\n"
                + "4.  add n/[NAME] a/[AMOUNT SPENT] c/[CATEGORY] (optional: d/[YYYY-MM-DD])\n"
                + "5.  delete i/[INDEX] c/[Category]\n"
                + "6.  sort [TYPE]\n"
                + "7.  view m/[MONTH] y/[YEAR]\n"
                + "8.  view current\n"
                + "9.  budget add c/[CATEGORY NAME] b/[BUDGET]\n"
                + "10. budget edit c/[CATEGORY NAME] b/[BUDGET]\n"
                + "11. budget list c/[CATEGORY NAME] \n"
                + "12. budget savings c/[CATEGORY NAME] s/[STARTMONTHYEAR (MM/YYYY)] e/[ENDMONTHYEAR (MM/YYYY)]\n"
                + "13. schedule d/[DD/MM/YYYY] a/[AMOUNT] n/[DESCRIPTION]\n"
                + "14. graph\n"
                + "15. moo\n"
                + "16. bye\n";
        Ui.setOutput(text);
    }
}
