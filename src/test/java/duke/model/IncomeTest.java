package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class IncomeTest {
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
    private static final String DEFAULT_DESCRIPTION = "";

    private static final BigDecimal TEST_AMOUNT = new BigDecimal("100.23");
    private static final String TEST_DESCRIPTION = "test description";

    private static final String INVALID_STORAGE_STRING = "amount:100.223\n" + "d:1\n";
    private static final String INVALID_AMOUNT = "test amount";


    private static final String ACTUAL_TO_STORAGE_STRING = "amount:100.23\n" + "description:test description";

    @Test
    void testDefaults() {
        Income testIncome = new Income.Builder().build();
        assertEquals(testIncome.getAmount(), DEFAULT_AMOUNT);
        assertEquals(testIncome.getDescription(), DEFAULT_DESCRIPTION);
    }

    @Test
    void testBuilderFromIncome() throws DukeException {
        Income testIncome = new Income.Builder()
                .setAmount(TEST_AMOUNT)
                .setDescription(TEST_DESCRIPTION)
                .build();
        Income testIncomeTwo = new Income.Builder(testIncome).build();
        assertEquals(testIncome.getAmount(), testIncomeTwo.getAmount());
        assertEquals(testIncome.getDescription(), testIncomeTwo.getDescription());
        assertEquals(testIncome.getAmount(), testIncomeTwo.getAmount());
    }

    @Test
    void testAmount() throws DukeException {
        Income testIncome1 = new Income.Builder().setAmount(TEST_AMOUNT).build();
        assertEquals(testIncome1.getAmount(), TEST_AMOUNT);

        try {
            new Income.Builder().setAmount(INVALID_AMOUNT);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_INCOME_AMOUNT_INVALID, INVALID_AMOUNT), e.getMessage());
        }
    }

    @Test
    void testDescription() {
        Income testIncome = new Income.Builder().setDescription(TEST_DESCRIPTION).build();
        assertEquals(testIncome.getDescription(), TEST_DESCRIPTION);
    }

    @Test
    void testToStorageString() throws DukeException {
        String storageString = new Income.Builder()
                .setAmount(TEST_AMOUNT)
                .setDescription(TEST_DESCRIPTION)
                .build()
                .toStorageString();
        assertEquals(storageString, ACTUAL_TO_STORAGE_STRING);
        Income testIncome = new Income.Builder(storageString).build();
        assertEquals(testIncome.getAmount(), TEST_AMOUNT);
        assertEquals(testIncome.getDescription(), TEST_DESCRIPTION);
    }

    @Test
    void testInvalidStorageString() {
        try {
            new Income.Builder(INVALID_STORAGE_STRING);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_EXPENSE_AMOUNT_INVALID, "100.223"), e.getMessage());
        }
    }
}
