package moomoo.command;

import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.stubs.AddExpenditureCommandStub;
import moomoo.stubs.BudgetStub;
import moomoo.stubs.ScheduleListStub;
import moomoo.stubs.StorageStub;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationCommandTest {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    @Test
    void testNotification() throws MooMooException {
        HashMap<String, Double> categoryBudgets = new HashMap<>();
        categoryBudgets.put("food", 100.0);
        BudgetStub budget = new BudgetStub(categoryBudgets);
        StorageStub storage = new StorageStub();
        CategoryList categoryList = new CategoryList();
        categoryList.add(new Category("food"));
        String colour = "";
        String reset = "";
        DetectOsCommand getOs = new DetectOsCommand();
        if (!getOs.osName.contains("win")) {
            colour = ANSI_GREEN;
            reset = ANSI_RESET;
        }
        /**
         * Test alert when budget not exceeded.
         */
        AddExpenditureCommandStub addExpenditure = new AddExpenditureCommandStub("mala", 10.0,
                LocalDate.now(), "food");
        ScheduleListStub calendar = new ScheduleListStub();
        addExpenditure.execute(calendar, budget, categoryList, storage);
        assertEquals(colour + "                     .-------------------------------------------------.\n"
               + "  ,/         \\,      |  __  __  ___   ___                              |\n"
               + " ((__,-\"\"\"-,__))     | |  \\/  |/ _ \\ / _ \\                             |\n"
               + " .-'(       )`-,     | | |\\/| | (_) | (_) |                            |\n"
               + " `~~` d\\ /b `~~`     | |_|  |_|\\___/ \\___/                             |\n"
               + "     |     |         |                                                 |\n"
               + "     (6___6)         | Budget remaining : 90.0                         |\n"
               + "      `---`          .-------------------------------------------------.\n" + reset,
                Ui.getOutput());
        /**
         * Test alert when expenditure is reaching the budget limit.
         */
        if (!getOs.osName.contains("win")) {
            colour = ANSI_YELLOW;
            reset = ANSI_RESET;
        }
        AddExpenditureCommandStub addExpenditure2 = new AddExpenditureCommandStub("pasta", 85.0,
                LocalDate.now(), "food");
        addExpenditure2.execute(calendar, budget, categoryList, storage);
        assertEquals(colour + "                     .-------------------------------------------------.\n"
                        + "  ,/         \\,      |  __  __  ___   ___                              |\n"
                        + " ((__,-\"\"\"-,__))     | |  \\/  |/ _ \\ / _ \\                             |\n"
                        + " .-'(       )`-,     | | |\\/| | (_) | (_) |                            |\n"
                        + " `~~` d\\ /b `~~`     | |_|  |_|\\___/ \\___/                             |\n"
                        + "     |     |         | You are reaching your budget limit!             |\n"
                        + "     (6___6)         | Budget remaining : 5.0                          |\n"
                        + "      `---`          .-------------------------------------------------.\n" + reset,
                Ui.getOutput());
        /**
         * Test alert when expenditure has reached the budget limit.
         */
        if (!getOs.osName.contains("win")) {
            colour = ANSI_RED;
            reset = ANSI_RESET;
        }
        AddExpenditureCommandStub addExpenditureReached = new AddExpenditureCommandStub("fries", 5.0,
                LocalDate.now(), "food");
        addExpenditureReached.execute(calendar, budget, categoryList, storage);
        assertEquals(colour + "                     .-------------------------------------------------.\n"
                        + "  ,/         \\,      |  __  __  ___   ___                              |\n"
                        + " ((__,-\"\"\"-,__))     | |  \\/  |/ _ \\ / _ \\                             |\n"
                        + " .-'(       )`-,     | | |\\/| | (_) | (_) |                            |\n"
                        + " `~~` d\\ /b `~~`     | |_|  |_|\\___/ \\___/                             |\n"
                        + "     |     |         | You have reached your budget limit!             |\n"
                        + "     (6___6)         | Budget remaining : 0.0                          |\n"
                        + "      `---`          .-------------------------------------------------.\n" + reset,
                Ui.getOutput());
        /**
         * Test alert when budget has been exceeded.
         */
        if (!getOs.osName.contains("win")) {
            colour = ANSI_RED;
            reset = ANSI_RESET;
        }
        AddExpenditureCommandStub addExpenditureExceed = new AddExpenditureCommandStub("waffles", 15.0,
                LocalDate.now(), "food");
        addExpenditureExceed.execute(calendar, budget, categoryList, storage);
        assertEquals(colour + "                     .-------------------------------------------------.\n"
                        + "  ,/         \\,      |  __  __  ___   ___                              |\n"
                        + " ((__,-\"\"\"-,__))     | |  \\/  |/ _ \\ / _ \\                             |\n"
                        + " .-'(       )`-,     | | |\\/| | (_) | (_) |                            |\n"
                        + " `~~` d\\ /b `~~`     | |_|  |_|\\___/ \\___/                             |\n"
                        + "     |     |         | You have exceeded your budget for food!         |\n"
                        + "     (6___6)         | Budget remaining : -15.0                        |\n"
                        + "      `---`          .-------------------------------------------------.\n" + reset,
                Ui.getOutput());
    }
}
