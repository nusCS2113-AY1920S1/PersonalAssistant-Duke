package duke.model.payment;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PaymentInMonthPredicateTest {

    private static final LocalDate THIS_MONTH = LocalDate.now();
    private static final String THIS_MONTH_DUE = THIS_MONTH.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private static final LocalDate LAST_MONTH = LocalDate.now().minusMonths(1);
    private static final String LAST_MONTH_DUE = LAST_MONTH.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private static final LocalDate NEXT_MONTH = LocalDate.now().plusMonths(1);
    private static final String NEXT_MONTH_DUE = NEXT_MONTH.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private PaymentInMonthPredicate predicate = new PaymentInMonthPredicate();

    @Test
    public void test_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_paymentDueBefore_returnsFalse() throws DukeException {
        assertFalse(predicate.test(new Payment.Builder().setDue(LAST_MONTH_DUE).build()));
    }

    @Test
    public void test_paymentDueInMonth_returnsTrue() throws DukeException {
        assertTrue(predicate.test(new Payment.Builder().setDue(THIS_MONTH_DUE).build()));
    }

    @Test
    public void test_paymentDueAfterMonth_returnFalse() throws DukeException {
        assertFalse(predicate.test(new Payment.Builder().setDue(NEXT_MONTH_DUE).build()));
    }
}
