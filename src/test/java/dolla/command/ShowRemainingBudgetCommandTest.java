package dolla.command;

import dolla.parser.ParserStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class ShowRemainingBudgetCommandTest implements ParserStringList {

    @Test
    public void showDailyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingBudgetCommand(LIMIT_DURATION_D);
        String expected = LIMIT_COMMAND_REMAINING + SPACE + LIMIT_DURATION_D;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showWeeklyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingBudgetCommand(LIMIT_DURATION_W);
        String expected = LIMIT_COMMAND_REMAINING + SPACE + LIMIT_DURATION_W;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }

    @Test
    public void showMonthlyBudgetTest() {
        Command weeklyBudgetCommand = new ShowRemainingBudgetCommand(LIMIT_DURATION_M);
        String expected = LIMIT_COMMAND_REMAINING + SPACE + LIMIT_DURATION_M;
        assertEquals(expected, weeklyBudgetCommand.getCommandInfo());
    }
}
