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

public class PaymentOverduePredicateTest {

    private static final LocalDate LAST_DAY = LocalDate.now().minusDays(1);
    private static final String LAST_DAY_DUE = LAST_DAY.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    private static final LocalDate NEXT_DAY = LocalDate.now().plusDays(1);
    private static final String NEXT_DAY_DUE = NEXT_DAY.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


    private PaymentOverduePredicate predicate = new PaymentOverduePredicate();

    @Test
    public void test_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> predicate.test(null));
    }

    @Test
    public void test_paymentOverdue_returnsTrue() throws DukeException {
        assertTrue(predicate.test(new Payment.Builder().setDue(LAST_DAY_DUE).build()));
    }

    @Test
    public void test_paymentNotOverdue_returnsFalse() throws DukeException {
        assertFalse(predicate.test(new Payment.Builder().setDue(NEXT_DAY_DUE).build()));
    }
}
