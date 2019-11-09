package dolla.command;

import dolla.parser.ParserStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class ShowRemainingLimitCommandTest implements ParserStringList {

    @Test
    public void showDailyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_D, LIMIT_TYPE_B);
        String expected = LIMIT_DURATION_D + SPACE + LIMIT_TYPE_B;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showWeeklyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_W, LIMIT_TYPE_B);
        String expected = LIMIT_DURATION_W + SPACE + LIMIT_TYPE_B;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showMonthlyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_M, LIMIT_TYPE_B);
        String expected = LIMIT_DURATION_M + SPACE + LIMIT_TYPE_B;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showDailySavingTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_D, LIMIT_TYPE_S);
        String expected = LIMIT_DURATION_D + SPACE + LIMIT_TYPE_S;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showWeeklySavingTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_W, LIMIT_TYPE_S);
        String expected = LIMIT_DURATION_W + SPACE + LIMIT_TYPE_S;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showMonthlySavingTest() {
        Command weeklyBudgetCommand = new ShowRemainingLimitCommand(LIMIT_DURATION_M, LIMIT_TYPE_S);
        String expected = LIMIT_DURATION_M + SPACE + LIMIT_TYPE_S;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }
}
