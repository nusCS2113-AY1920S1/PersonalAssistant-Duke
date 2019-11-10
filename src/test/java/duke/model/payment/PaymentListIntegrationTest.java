package duke.model.payment;

import duke.exception.DukeException;
import duke.model.payment.Payment.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentListIntegrationTest {

    private static final String RELEVANT_KEYWORD = "john";
    private static final String IRRELEVANT_KEYWORD = "transportation";
    private static final String UNIDENTIFIABLE_SORTING_CRITERIA = "description";
    private static final String AMOUNT_SORTING_CRITERIA = "amount";
    private static final String PRIORITY_SORTING_CRITERIA = "priority";
    private static final int PAYMENTS_FULL_SIZE = 5;

    private static Payment STORAGE_FEE;
    private static Payment ORIENTATION_FEE;
    private static Payment HALL_MEAL;
    private static Payment HOSTEL_FEE;
    private static Payment RETURN_MONEY;

    private final PaymentList payments = new PaymentList();

    @BeforeEach
    public void setup() {
        assertDoesNotThrow(() -> {
            STORAGE_FEE = new Builder().setDescription("In Room Storage Fee")
                    .setDue("09/09/2019")
                    .setAmount("150").setPriority("medium").build();

            ORIENTATION_FEE = new Builder().setDescription("Orientation Fee")
                    .setDue("08/10/2019")
                    .setAmount("30").setPriority("low").build();

            HALL_MEAL = new Builder().setDescription("Hall Meal Fee for Next semester")
                    .setDue("23/11/2019")
                    .setAmount("350").setPriority("low").build();

            HOSTEL_FEE = new Builder().setDescription("Hostel_Preservation_Fee")
                    .setDue("15/10/2019")
                    .setAmount("200").setPriority("high").build();

            RETURN_MONEY = new Builder().setDescription("Return Money to John")
                    .setDue("29/11/2019")
                    .setAmount("36").setPriority("medium").build();
        });
    }

    @Test
    public void add_nullPayment_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> payments.add(null));
    }

    @Test
    public void add_validPayment_success() {
        assertDoesNotThrow(() -> payments.add(HALL_MEAL));
    }

    @Test
    public void remove_indexOutOfBound_throwsDukeException() {
        payments.add(ORIENTATION_FEE);
        assertThrows(DukeException.class, () -> payments.remove(0)); // 1-based index
    }

    @Test
    public void remove_validIndex_success() {
        payments.add(ORIENTATION_FEE);
        assertDoesNotThrow(() -> payments.remove(1)); // 1-based index
        assertTrue(payments.getInternalList().isEmpty());
    }

    @Test
    public void setPayment_invalidIndex_throwsDukeException() {
        payments.add(STORAGE_FEE);
        assertThrows(DukeException.class, () -> payments.setPayment(0, HALL_MEAL)); // 1-based
    }

    @Test
    public void setPayment_nullEditedPayment_throwsNullPointerException() {
        payments.add(STORAGE_FEE);
        assertThrows(NullPointerException.class, () -> payments.setPayment(1, null)); // 1-based index
    }

    @Test
    public void setPayment_editedPaymentIsSamePayment_success() {
        payments.add(HOSTEL_FEE);
        assertDoesNotThrow(() -> payments.setPayment(1, HOSTEL_FEE)); // 1-based index

        PaymentList expectedPayments = new PaymentList();
        expectedPayments.add(HOSTEL_FEE);
        assertEquals(expectedPayments.getInternalList(), payments.getInternalList());
    }

    @Test
    public void setPayment_editedPaymentIsDifferentPayment_success() {
        payments.add(HOSTEL_FEE);
        assertDoesNotThrow(() -> payments.setPayment(1, ORIENTATION_FEE)); // 1-based index

        PaymentList expectedPayments = new PaymentList();
        expectedPayments.add(ORIENTATION_FEE);
        assertEquals(expectedPayments.getInternalList(), payments.getInternalList());
    }

    @Test
    public void searchPredicate_nullKeyword_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> payments.setSearchPredicate(null));
    }

    @Test
    public void searchPredicate_irrelevantKeyword_noResultsFound() {
        fillFullPayments();
        assertDoesNotThrow(() -> payments.setSearchPredicate(IRRELEVANT_KEYWORD));
        assertTrue(payments.asUnmodifiableFilteredList().isEmpty());
    }

    @Test
    public void searchPredicate_relevantKeyword_ResultsFound() {
        fillFullPayments();
        assertDoesNotThrow(() -> payments.setSearchPredicate(RELEVANT_KEYWORD));

        PaymentList expectedPayments = new PaymentList();
        expectedPayments.add(RETURN_MONEY);
        assertEquals(expectedPayments.asUnmodifiableFilteredList(),
                payments.asUnmodifiableFilteredList());
    }

    @Test
    public void setSortingCriteria_nullSortingCriteria_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> payments.setSortingCriteria(null));
    }

    @Test
    public void setSortingCriteria_unidentifiableSortingCriteria_throwsDukeException() {
        assertThrows(DukeException.class,
                () -> payments.setSortingCriteria(UNIDENTIFIABLE_SORTING_CRITERIA));
    }

    @Test
    public void setSortingCriteria_amount_paymentsSortedByAmount() throws DukeException {
        fillFullPayments();
        assertDoesNotThrow(() -> payments.setSortingCriteria(AMOUNT_SORTING_CRITERIA));

        for (int index = 0; index < PAYMENTS_FULL_SIZE - 1; index++) { // 0-based index
            BigDecimal thisAmount = payments.asUnmodifiableFilteredList().get(index).getAmount();
            BigDecimal nextAmount = payments.asUnmodifiableFilteredList().get(index + 1).getAmount();
            assertTrue(thisAmount.compareTo(nextAmount) >= 0);
        }
    }

    @Test
    public void setSortingCriteria_priority_paymentsSortedByPriority() throws DukeException {
        fillFullPayments();
        assertDoesNotThrow(() -> payments.setSortingCriteria(PRIORITY_SORTING_CRITERIA));

        for (int index = 0; index < PAYMENTS_FULL_SIZE - 1; index++) { // 0-based
            int thisPriority = payments.asUnmodifiableFilteredList().get(index).getNumeratedPriority();
            int nextPriority = payments.asUnmodifiableFilteredList().get(index + 1).getNumeratedPriority();
            assertTrue(thisPriority >= nextPriority);
        }
    }

    @Test
    public void asUnmodifiableFilteredList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> payments.asUnmodifiableFilteredList().remove(0)); // 0-based
    }

    /**
     * Fills the {@code payments} with all five samples.
     */
    private void fillFullPayments() {
        payments.add(STORAGE_FEE);
        payments.add(ORIENTATION_FEE);
        payments.add(HALL_MEAL);
        payments.add(HOSTEL_FEE);
        payments.add(RETURN_MONEY);
    }
}
