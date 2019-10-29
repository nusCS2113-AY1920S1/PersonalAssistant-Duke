package duke.model.payment;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PaymentOutOfDatePredicate implements Predicate<Payment> {

    public PaymentOutOfDatePredicate() {

    }

    @Override
    public boolean test(Payment payment) {

        LocalDate due = payment.getDue();
        LocalDate current = LocalDate.now();

        return due.isBefore(current);
    }
}
