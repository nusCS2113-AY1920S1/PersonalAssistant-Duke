package duke.model.payment;

import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {

    private static final Payment.Builder NULL_BUILDER = null;
    private static final String NULL_STRING = null;

    private static final String DESCRIPTION_FOR_TEST = "Orientation Fee";

    private static final String RECEIVER_FOR_TEST = "OSA";

    private static final String DUE_FOR_TEST = "09/09/2019";
    private static final String INVALID_DUE_FOR_TEST = "9/9/2019";

    private static final String TAG_FOR_TEST = "School Life";

    private static final String AMOUNT_FOR_TEST = "30.5";
    private static final String INVALID_AMOUNT_FOR_TEST = "thirty.five";

    private static final String PRIORITY_FOR_TEST = "Medium";
    private static final String INVALID_PRIORITY_FOR_TEST = "Very High";
    private static final int NUMERATED_PRIORITY_FOR_TEST = 2;

    private static final String KEYWORD_FOUND = "Orientation";
    private static final String KEYWORD_NOT_FOUND = "Transportation";

    private Payment.Builder paymentBuilder = new Payment.Builder();

    @Test
    public void constructor_nullBuilder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Payment(NULL_BUILDER));
    }

    @Test
    public void getDescription() {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setDescription(NULL_STRING));

        Payment payment = paymentBuilder.setDescription(DESCRIPTION_FOR_TEST).build();

        assertEquals(DESCRIPTION_FOR_TEST, payment.getDescription());
    }

    @Test
    public void getReceiver() {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setReceiver(NULL_STRING));

        Payment payment = paymentBuilder.setReceiver(RECEIVER_FOR_TEST).build();

        assertEquals(RECEIVER_FOR_TEST, payment.getReceiver());
    }

    @Test
    public void getDue() throws DukeException {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setReceiver(NULL_STRING));
        assertThrows(DukeException.class, () -> paymentBuilder.setDue(INVALID_DUE_FOR_TEST));

        Payment payment = paymentBuilder.setDue(DUE_FOR_TEST).build();

        assertEquals(DUE_FOR_TEST, payment.getDue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Test
    public void getTag() {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setTag(NULL_STRING));

        Payment payment = paymentBuilder.setTag(TAG_FOR_TEST).build();

        assertEquals(TAG_FOR_TEST, payment.getTag());
    }

    @Test
    public void getAmount() throws DukeException {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setAmount(NULL_STRING));
        assertThrows(DukeException.class, () -> paymentBuilder.setAmount(INVALID_AMOUNT_FOR_TEST));

        Payment payment = paymentBuilder.setAmount(AMOUNT_FOR_TEST).build();

        assertEquals(AMOUNT_FOR_TEST, payment.getAmount().toString());
    }

    @Test
    public void getPriority() throws DukeException {
        assertThrows(NullPointerException.class, () -> paymentBuilder.setPriority(NULL_STRING));
        assertThrows(DukeException.class, () -> paymentBuilder.setPriority(INVALID_PRIORITY_FOR_TEST));

        Payment payment = paymentBuilder.setPriority(PRIORITY_FOR_TEST).build();

        assertEquals(PRIORITY_FOR_TEST, payment.getPriority());
        // Tests the getNumerated() method by the way.
        assertEquals(NUMERATED_PRIORITY_FOR_TEST, payment.getNumeratedPriority());
    }

    @Test
    public void containsKeyword() throws DukeException {
        Payment payment = paymentBuilder.setDescription(DESCRIPTION_FOR_TEST)
                .setReceiver(RECEIVER_FOR_TEST)
                .setTag(TAG_FOR_TEST).build();

        assertTrue(payment.containsKeyword(KEYWORD_FOUND));
        assertFalse(payment.containsKeyword(KEYWORD_NOT_FOUND));
    }
}
