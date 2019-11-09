package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Tests whether a {@code payment} is overdue.
 */
public class PaymentOverduePredicate implements Predicate<Payment> {

    public PaymentOverduePredicate() {

    }

    @Override
    public boolean test(Payment payment) {
        requireNonNull(payment);

        LocalDate due = payment.getDue();
        LocalDate now = LocalDate.now();

        return due.isBefore(now);
    }
}
