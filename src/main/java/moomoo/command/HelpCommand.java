package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.category.CategoryList;

import java.util.ArrayList;

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

        ArrayList<String> text = new ArrayList<>();
        text.add(ANSI_GREEN + "Try one of these commands:" + ANSI_RESET);
        text.add("");
        text.add("1.  list");
        text.add("2.  category add [CATEGORY NAME]");
        text.add("3.  category delete [CATEGORY NAME or CATEGORY INDEX NUMBER]");
        text.add("4.  add n/[NAME] a/[AMOUNT SPENT] c/[CATEGORY] (optional: d/[d/MM/yyyy)");
        text.add("5.  delete i/[INDEX] c/[Category]");
        text.add("6.  sort [TYPE]");
        text.add("7.  view m/[MONTH] y/[YEAR]");
        text.add("8.  view current");
        text.add("9.  view all");
        text.add("10. budget add c/[CATEGORY NAME] b/[BUDGET]");
        text.add("11. budget edit c/[CATEGORY NAME] b/[BUDGET]");
        text.add("12. budget list c/[CATEGORY NAME] ");
        text.add("13. budget savings c/[CATEGORY NAME] s/[STARTMONTHYEAR (MM/YYYY)] e/[ENDMONTHYEAR (MM/YYYY)]");
        text.add("14. schedule d/[DD/MM/YYYY] a/[AMOUNT] n/[DESCRIPTION]");
        text.add("15. graph");
        text.add("16. moo");
        text.add("17. bye");
        Ui.showInCowBox(text);
    }
}
