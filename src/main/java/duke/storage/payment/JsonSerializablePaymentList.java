package duke.storage.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import duke.exception.DukeException;
import duke.model.payment.Payment;
import duke.model.payment.PaymentList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * A PaymentList that is serializable to JSON format.
 */
@JsonRootName(value = "paymentList")
public class JsonSerializablePaymentList {

    private final List<JsonAdaptedPayment> payments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePaymentList} with the given payments.
     */
    @JsonCreator
    public JsonSerializablePaymentList(@JsonProperty("payments") List<JsonAdaptedPayment> payments) {
        this.payments.addAll(payments);
    }

    /**
     * Converts a given {@code PaymentList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePaymentList}.
     */
    public JsonSerializablePaymentList(PaymentList source) {
        requireNonNull(source);

        payments.addAll(source.getInternalList().stream()
                .map(JsonAdaptedPayment::new).collect(Collectors.toList()));
    }

    /**
     * Converts this {@code payments} into the model's {@code PaymentList} object.
     *
     * @throws DukeException if there were any data constraints violated.
     */
    public PaymentList toModelType() throws DukeException {
        List<Payment> internalPaymentList = new ArrayList<>();
        for (JsonAdaptedPayment jsonAdaptedPayment : payments) {
            Payment payment = jsonAdaptedPayment.toModelType();
            internalPaymentList.add(payment);
        }
        return new PaymentList(internalPaymentList);
    }

}
