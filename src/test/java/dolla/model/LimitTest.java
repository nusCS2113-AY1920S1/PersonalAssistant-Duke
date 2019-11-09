package dolla.model;

import org.junit.jupiter.api.Test;

import static dolla.parser.ParserStringList.LIMIT_DURATION_D;
import static dolla.parser.ParserStringList.LIMIT_DURATION_W;
import static dolla.parser.ParserStringList.LIMIT_TYPE_B;
import static dolla.parser.ParserStringList.LIMIT_TYPE_S;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Weng-Kexin
public class LimitTest {

    private Limit newBudget = createNewLimit(LIMIT_TYPE_B, 5, LIMIT_DURATION_D);
    private Limit newSaving = createNewLimit(LIMIT_TYPE_S, 50, LIMIT_DURATION_W);

    private Limit createNewLimit(String limitType, double amount, String limitDuration) {
        return new Limit(limitType, amount, limitDuration);
    }

    @Test
    public void getBudgetDetailTest() {
        String expected = "[budget] [$5.0] [daily]";
        assertEquals(expected, newBudget.getRecordDetail());
    }

    @Test
    public void budgetFormatSaveTest() {
        String expected = "BU | 5.0 | daily";
        assertEquals(expected, newBudget.formatSave());
    }

    @Test
    public void budgetAmountToMoneyTest() {
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

    @Test
    public void getSavingDetailTest() {
        String expected = "[saving] [$50.0] [weekly]";
        assertEquals(expected, newSaving.getRecordDetail());
    }

    @Test
    public void savingFormatSaveTest() {
        String expected = "S | 50.0 | weekly";
        assertEquals(expected, newSaving.formatSave());
    }

    @Test
    public void savingAmountToMoneyTest() {
        assertEquals("$50.0", newSaving.amountToMoney());
    }

    @Test
    public void getNewSavingTypeTest() {
        assertEquals(LIMIT_TYPE_S, newSaving.getType());
    }

    @Test
    public void getNewSavingDurationTest() {
        assertEquals(LIMIT_DURATION_W, newSaving.getDuration());
    }
}
