package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Tests whether a {@code payment} is due in current week.
 */
public class PaymentInWeekPredicate implements Predicate<Payment> {

    public PaymentInWeekPredicate() {

    }

    @Override
    public boolean test(Payment payment) {
        requireNonNull(payment);

        LocalDate now = LocalDate.now();
        LocalDate due = payment.getDue();

        // The current day of week. e.g. Wednesday corresponds to 3
        int dayOfWeek = now.getDayOfWeek().getValue();

        // Monday of current week
        LocalDate thisMonday = now.minusDays(dayOfWeek - 1);

        // Sunday of current week
        LocalDate thisSunday = now.plusDays(7 - dayOfWeek);

        return (due.equals(thisSunday)
                || due.equals(thisMonday)
                || (due.isAfter(thisMonday) && due.isBefore(thisSunday)));
    }
}
