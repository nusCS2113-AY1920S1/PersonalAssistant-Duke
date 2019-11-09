package duke.model.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SearchKeywordPredicateTest {

    private static final String DESCRIPTION = "Orientation Fee";
    private static final String DESCRIPTION_KEYWORD = "Orientation";
    private static final String MIXED_CASE_DESCRIPTION_KEYWORD = "oRIentaTion";

    private static final String RECEIVER = "OSA";
    private static final String RECEIVER_KEYWORD = "OSA";
    private static final String MIXED_CASE_RECEIVER_KEYWORD = "osa";

    private static final String TAG = "School Life";
    private static final String TAG_KEYWORD = "School";
    private static final String MIXED_CASE_TAG_KEYWORD = "school";

    private static final String KEYWORD_NOT_FOUND = "Transportation";

    @Test
    public void test_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SearchKeywordPredicate(null));
    }

    @Test
    public void test_descriptionContainsKeyword_returnsTrue() {

        Payment payment = new Payment.Builder().setDescription(DESCRIPTION).build();

        // Searches in description
        SearchKeywordPredicate predicate = new SearchKeywordPredicate(DESCRIPTION_KEYWORD);
        assertTrue(predicate.test(payment));

        // Mixed-case description keywords
        predicate = new SearchKeywordPredicate(MIXED_CASE_DESCRIPTION_KEYWORD);
        assertTrue(predicate.test(payment));
    }

    @Test
    public void test_receiverContainsKeyword_returnsTrue() {

        Payment payment = new Payment.Builder().setReceiver(RECEIVER).build();

        // Searches in receiver
        SearchKeywordPredicate predicate = new SearchKeywordPredicate(RECEIVER_KEYWORD);
        assertTrue(predicate.test(payment));

        // Mixed-case receiver keywords
        predicate = new SearchKeywordPredicate(MIXED_CASE_RECEIVER_KEYWORD);
        assertTrue(predicate.test(payment));
    }

    @Test
    public void test_tagContainsKeyword_returnsTrue() {

        Payment payment = new Payment.Builder().setTag(TAG).build();

        // Searches in tag
        SearchKeywordPredicate predicate = new SearchKeywordPredicate(TAG_KEYWORD);
        assertTrue(predicate.test(payment));

        // Mixed-case tag keywords
        predicate = new SearchKeywordPredicate(MIXED_CASE_TAG_KEYWORD);
        assertTrue(predicate.test(payment));
    }

    @Test
    public void test_keywordNotFound_returnsFalse() {
        Payment payment = new Payment.Builder().setDescription(DESCRIPTION)
                .setReceiver(RECEIVER)
                .setTag(TAG).build();

        SearchKeywordPredicate predicate = new SearchKeywordPredicate(KEYWORD_NOT_FOUND);
        assertFalse(predicate.test(payment));
    }
}
