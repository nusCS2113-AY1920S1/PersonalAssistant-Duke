package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class IncomeListTest {
    private static final BigDecimal TEST_AMOUNT = new BigDecimal("233.03");
    private static final String TEST_DESCRIPTION = "test description";
    private static final String ACTUAL_STORAGE_STRING = "amount:233.03\n" + "description:test description\n";
    private static final String ACTUAL_TOTAL_STRING = "Total Income: $460.13";

    @Test
    void testBasicOperations() throws DukeException {
        IncomeList testIncomeList = new IncomeList(new ArrayList<>());
        Income testIncome = new Income.Builder().build();
        testIncomeList.add(testIncome);
        assertEquals(testIncomeList.get(1), testIncome);
        assertEquals(testIncomeList.internalSize(), 1);
        testIncomeList.remove(1);
        assertEquals(testIncomeList.internalSize(), 0);
        testIncomeList.add(testIncome);
        testIncomeList.add(testIncome);
        testIncomeList.clear();
        assertEquals(testIncomeList.internalSize(), 0);
    }

    @Test
    void testInvalidBasicOperations() {
        IncomeList testIncomeList = new IncomeList(new ArrayList<>());
        Income testIncome = new Income.Builder().build();
        testIncomeList.add(testIncome);

        try {
            testIncomeList.get(2);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, "income", 2), e.getMessage());
        }

        try {
            testIncomeList.remove(2);
            fail();
        } catch (DukeException e) {
            assertEquals(String.format(DukeException.MESSAGE_NO_ITEM_AT_INDEX, "income", 2), e.getMessage());
        }
    }

    @Test
    void testListOperations() {
        IncomeList testIncomeList = new IncomeList(new ArrayList<>());
        Income testIncome = new Income.Builder().build();
        testIncomeList.add(testIncome);
        Assertions.assertNotNull(testIncomeList.getInternalList());
        Assertions.assertFalse(testIncomeList.getInternalList().isEmpty());
        Assertions.assertNotNull(testIncomeList.getExternalList());
        Assertions.assertFalse(testIncomeList.getExternalList().isEmpty());
    }

    @Test
    void testItemFromStringStorage() throws DukeException {
        Income testIncome = IncomeList.itemFromStorageString(ACTUAL_STORAGE_STRING);
        assertEquals(testIncome.getAmount(),TEST_AMOUNT);
        assertEquals(testIncome.getDescription(),TEST_DESCRIPTION);
    }

    @Test
    void testGetTotalAmount() throws DukeException {
        IncomeList testIncomeList = new IncomeList(new ArrayList<>());
        Income testIncomeOne = new Income.Builder().build();
        Income testIncomeTwo = new Income.Builder().setAmount("9").build();
        Income testIncomeThree = new Income.Builder().setAmount("300").build();
        Income testIncomeFour = new Income.Builder().setAmount("30.9").build();
        Income testIncomeFive = new Income.Builder().setAmount("120.23").build();
        testIncomeList.add(testIncomeOne);
        testIncomeList.add(testIncomeTwo);
        testIncomeList.add(testIncomeThree);
        testIncomeList.add(testIncomeFour);
        testIncomeList.add(testIncomeFive);
        assertEquals(testIncomeList.getTotalExternalAmount(), new BigDecimal("460.13"));
        String testTotalString = testIncomeList.getTotalString().get();
        assertEquals(testTotalString, ACTUAL_TOTAL_STRING);
    }

}
