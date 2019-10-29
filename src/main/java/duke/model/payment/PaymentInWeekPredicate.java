package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PaymentInWeekPredicate implements Predicate<Payment> {

    public PaymentInWeekPredicate() {

    }

    @Override
    public boolean test(Payment payment) {

        LocalDate due = payment.getDue();
        int dayOfWeek = due.getDayOfWeek().getValue();
        LocalDate thisMonday = payment.getDue().minusDays(dayOfWeek - 1); // Sunday of week of expense.
        LocalDate thisSunday = payment.getDue().plusDays(7 - dayOfWeek); // Monday of week of expense.
        LocalDate current = LocalDate.now();

        return (current.equals(thisSunday) || current.equals(thisMonday)
                || (current.isAfter(thisMonday) && current.isBefore(thisSunday)));

    }
}
