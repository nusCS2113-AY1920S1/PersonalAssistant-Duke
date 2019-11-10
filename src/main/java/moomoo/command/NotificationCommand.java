package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;

public class NotificationCommand extends Command {
    private String cat;
    private double expenditure;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Alerts user if user exceeded the budget.
     *
     * @param cat         The name of the category that user just added his expenditure to.
     * @param expenditure The total current expenditure of that category.
     */
    public NotificationCommand(String cat, double expenditure) {
        super(false, "");
        this.cat = cat;
        this.expenditure = expenditure;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        String alert = "";
        if (expenditure > budget.getBudgetFromCategory(cat)) {
            alert = "You have exceeded your budget for " + cat + "!";
        } else if (expenditure == budget.getBudgetFromCategory(cat)) {
            alert = "You have reached your budget limit!";
        } else if (expenditure > budget.getBudgetFromCategory(cat) * 0.9) {
            alert = "You are reaching your budget limit!";         
        }
        int blank = 47 - alert.length();
        String blankSpace = " ";
        for (int i = 0; i < blank; i++) {
            blankSpace += " ";
        }
        double balance = budget.getBudgetFromCategory(cat) - expenditure;
        blank = 28 - String.valueOf(balance).length();
        String blank2 = " ";
        for (int i = 0; i < blank; i++) {
            blank2 += " ";
        }

        DetectOsCommand getOS = new DetectOsCommand();
        String osName = getOS.osName;
        String colour = "";
        String reset = "";
        if (!osName.contains("win")) {
            if (alert.length() < 1) {
                colour = ANSI_GREEN;
            } else {
                colour = ANSI_RED;
            }
            reset = ANSI_RESET;
        }
        String cow = colour
                + "                     .-------------------------------------------------.\n"
                + "  ,/         \\,      |  __  __  ___   ___                              |\n"
                + " ((__,-\"\"\"-,__))     | |  \\/  |/ _ \\ / _ \\                             |\n"
                + " .-'(       )`-,     | | |\\/| | (_) | (_) |                            |\n"
                + " `~~` d\\ /b `~~`     | |_|  |_|\\___/ \\___/                             |\n"
                + "     |     |         | " + alert + blankSpace + "|\n"
                + "     (6___6)         | " + "Budget remaining : " + balance + blank2 + "|\n"
                + "      `---`          .-------------------------------------------------." + reset;

        Ui.setOutput(cow);
    }
}

