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

public class PaymentInWeekPredicateTest {

    private static final LocalDate THIS_WEEK = LocalDate.now();
    private static final String THIS_WEEK_DUE = THIS_WEEK.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private static final LocalDate LAST_WEEK = LocalDate.now().minusWeeks(1);
    private static final String LAST_WEEK_DUE = LAST_WEEK.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private static final LocalDate NEXT_WEEK = LocalDate.now().plusWeeks(1);
    private static final String NEXT_WEEK_DUE = NEXT_WEEK.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private PaymentInWeekPredicate predicate = new PaymentInWeekPredicate();

    @Test
    public void test_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_paymentDueBefore_returnsFalse() throws DukeException {
        assertFalse(predicate.test(new Payment.Builder().setDue(LAST_WEEK_DUE).build()));
    }

    @Test
    public void test_paymentDueInWeek_returnsTrue() throws DukeException {
        assertTrue(predicate.test(new Payment.Builder().setDue(THIS_WEEK_DUE).build()));
    }

    @Test
    public void test_paymentDueAfterWeek_returnFalse() throws DukeException {
        assertFalse(predicate.test(new Payment.Builder().setDue(NEXT_WEEK_DUE).build()));
    }
}
