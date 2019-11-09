package duke.model.payment;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Tests whether a {@code Payment}'s description, receiver or tag contains the keyword given.
 * Ignores the letter case.
 */
public class SearchKeywordPredicate implements Predicate<Payment> {

    private String keyword;

    /**
     * Constructs an object of {@code SearchKeywordPredicate}
     * with the keyword set as the given {@code keyword}.
     *
     * @param keyword the keyword used for searching
     */
    public SearchKeywordPredicate(String keyword) {
        requireNonNull(keyword);

        this.keyword = keyword;
    }

    @Override
    public boolean test(Payment payment) {
        requireNonNull(payment);

        return payment.containsKeyword(keyword);
    }
}
