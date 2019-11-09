package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Tests whether a {@code payment} is due in current month.
 */
public class PaymentInMonthPredicate implements Predicate<Payment> {

    public PaymentInMonthPredicate() {

    }

    @Override
    public boolean test(Payment payment) {
        requireNonNull(payment);

        LocalDate due = payment.getDue();
        LocalDate now = LocalDate.now();

        boolean isSameYear = (due.getYear() == now.getYear());
        boolean isSameMonth = due.getMonth().equals(now.getMonth());

        return (isSameYear && isSameMonth);
    }
}
