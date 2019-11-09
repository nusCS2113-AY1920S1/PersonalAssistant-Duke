package duke.model;

import duke.exception.DukeException;
import duke.logic.parser.Parser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpenseTest {
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
    private static final String DEFAULT_DESCRIPTION = "";
    private static final boolean DEFAULT_TENTATIVE = false;
    private static final String DEFAULT_TIME = Parser.formatTime(LocalDateTime.now());

    private static final BigDecimal TEST_AMOUNT = new BigDecimal("1.23");
    private static final String TEST_DESCRIPTION = "test description";
    private static final boolean TEST_TENTATIVE = true;
    private static final String TEST_TIME = "18:00 01/01/2000";
    private static final String TEST_TAG = "test tag";

    private static final String INVALID_STORAGE_STRING = "tags:tag1 tag2 tag3\n"
            + "amount:1.223\n"
            + "d:1\n"
            + "t:2";

    private static final String ACTUAL_TO_STRING = "$1.23 "
            + "test description "
            + "18:00 01/01/2000 "
            + "(tentative) "
            + "tag1 tag2 tag 3 "
            + "isRecurring:false";
    private static final String ACTUAL_TO_STORAGE_STRING = "tag:test tag\n"
            + "amount:1.23\n"
            + "description:test description\n"
            + "time:18:00 01/01/2000\n"
            + "isTentative:true\n"
            + "isRecurring:false";

    @Test
    public void testDefaults() {
        Expense testExpense = new Expense.Builder().build();
        assertEquals(testExpense.getAmount(), DEFAULT_AMOUNT);
        assertEquals(testExpense.getDescription(), DEFAULT_DESCRIPTION);
        assertEquals(testExpense.isTentative(), DEFAULT_TENTATIVE);
        assertEquals(Parser.formatTime(testExpense.getTime()), DEFAULT_TIME);
        assertTrue(testExpense.getTag().isEmpty());
    }

    @Test
    public void testBuilderFromExpense() throws DukeException {
        Expense testExpense = new Expense.Builder()
                .setAmount(TEST_AMOUNT)
                .setDescription(TEST_DESCRIPTION)
                .setTentative(TEST_TENTATIVE)
                .setTime(TEST_TIME)
                .setTag(TEST_TAG)
                .build();
        Expense testExpenseTwo = new Expense.Builder(testExpense).build();
        assertEquals(testExpense.getAmount(), testExpenseTwo.getAmount());
        assertEquals(testExpense.getDescription(), testExpenseTwo.getDescription());
        assertEquals(testExpense.getTag(), testExpenseTwo.getTag());
        assertEquals(testExpense.getTime(), testExpenseTwo.getTime());
        assertEquals(testExpense.getAmount(), testExpenseTwo.getAmount());
    }

    @Test
    public void testAmount() throws DukeException {
        Expense testExpense = new Expense.Builder().setAmount(TEST_AMOUNT).build();
        assertEquals(testExpense.getAmount(), TEST_AMOUNT);
    }

    @Test
    public void testDescription() {
        Expense testExpense = new Expense.Builder().setDescription(TEST_DESCRIPTION).build();
        assertEquals(testExpense.getDescription(), TEST_DESCRIPTION);
    }

    @Test
    public void testIsTentative() {
        Expense testExpense = new Expense.Builder().setTentative(TEST_TENTATIVE).build();
        assertEquals(testExpense.isTentative(), TEST_TENTATIVE);
    }

    @Test
    public void testTime() throws DukeException {
        Expense testExpense = new Expense.Builder().setTime(TEST_TIME).build();
        assertEquals(Parser.formatTime(testExpense.getTime()), TEST_TIME);
    }

    @Test
    public void testTags() {
        Expense testExpense = new Expense.Builder().setTag(TEST_TAG).build();
        assertEquals(testExpense.getTag(), TEST_TAG);
    }


    @Test
    public void testToStorageString() throws DukeException {
        String storageString = new Expense.Builder()
                        .setAmount(TEST_AMOUNT)
                        .setDescription(TEST_DESCRIPTION)
                        .setTentative(TEST_TENTATIVE)
                        .setTime(TEST_TIME)
                        .setTag(TEST_TAG)
                        .build()
                        .toStorageString();
        assertEquals(storageString, ACTUAL_TO_STORAGE_STRING);
        Expense testExpense = new Expense.Builder(storageString).build();
        assertEquals(testExpense.getAmount(), TEST_AMOUNT);
        assertEquals(testExpense.getDescription(), TEST_DESCRIPTION);
        assertEquals(testExpense.isTentative(), TEST_TENTATIVE);
        assertEquals(Parser.formatTime(testExpense.getTime()), TEST_TIME);
        assertEquals(testExpense.getTag(), TEST_TAG);
    }

    @Test
    public void testInvalidStorageString() {
        try {
            new Expense.Builder(INVALID_STORAGE_STRING);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_EXPENSE_AMOUNT_INVALID, "1.223"), e.getMessage());
        }
    }
}
