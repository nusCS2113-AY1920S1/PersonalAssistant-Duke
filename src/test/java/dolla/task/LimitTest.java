package dolla.task;

import org.junit.jupiter.api.Test;

import static dolla.parser.ParserStringList.LIMIT_DURATION_D;
import static dolla.parser.ParserStringList.LIMIT_TYPE_B;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitTest {

    private Limit createDailyBudget() {
        return new Limit(LIMIT_TYPE_B, 5, LIMIT_DURATION_D);
    }

    private Limit newBudget = createDailyBudget();

    @Test
    public void getDailyBudgetDetailTest() {
        assertEquals("[" + LIMIT_TYPE_B + "] [$5.0] [" + LIMIT_DURATION_D + "]", newBudget.getRecordDetail());
    }

    @Test
    public void dailyBudgetFormatSaveTest() {
        assertEquals("BU | 5.0 | " + LIMIT_DURATION_D, newBudget.formatSave());
    }

    @Test
    public void dailyBudgetAmountToMoneyTest() {
        //Limit newBudget = createDailyBudget();
        assertEquals("$5.0", newBudget.amountToMoney());
    }

    @Test
    public void getNewBudgetTypeTest() {
        assertEquals(LIMIT_TYPE_B, newBudget.getType());
    }

    @Test
    public void getNewBudgetDurationTest() {
        assertEquals(LIMIT_DURATION_D, newBudget.getDuration());
    }
}
