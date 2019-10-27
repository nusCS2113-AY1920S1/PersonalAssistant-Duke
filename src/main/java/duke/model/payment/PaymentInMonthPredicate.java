package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PaymentInMonthPredicate implements Predicate<Payment> {

    public PaymentInMonthPredicate() {

    }

    @Override
    public boolean test(Payment payment) {

        LocalDate due = payment.getDue();
        LocalDate current = LocalDate.now();
        boolean isSameYear = due.getYear() == current.getYear();
        boolean isSameMonth = due.getMonth().equals(current.getMonth());

        return (isSameYear && isSameMonth);
    }
}
