package duke.model.payment;

import java.util.function.Predicate;

public class SearchKeywordPredicate implements Predicate <Payment> {

    private String keyword;

    public SearchKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Payment payment) {
        return payment.containsKeyword(keyword);
    }
}
