package duke.storage.payment;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exception.DukeException;
import duke.model.payment.Payment;

import java.time.format.DateTimeFormatter;

/**
 * Jackson-friendly version of Payment.
 */
public class JsonAdaptedPayment {

    private String description;
    private String receiver;
    private String due;
    private String remark;

    private String amount;
    private String priority;


    /**
     * Constructs a {@code JsonAdaptedPayment} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPayment(@JsonProperty("description") String description,
                              @JsonProperty("receiver") String receiver,
                              @JsonProperty("due") String due,
                              @JsonProperty("remark") String remark,
                              @JsonProperty("amount") String amount,
                              @JsonProperty("priority") String priority) {

        this.description = description;
        this.receiver = receiver;
        this.due = due;
        this.remark = remark;
        this.amount = amount;
        this.priority = priority;
    }



    /**
     * Converts a given {@code Payment} into this class for Jackson use.
     */
    public JsonAdaptedPayment(Payment source) {

        description = source.getDescription();
        receiver = source.getReceiver();
        due = source.getDue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        remark = source.getTag();
        amount = source.getAmount().toString();
        priority = source.getPriority().toString();

    }

    /**
     * Converts this Jackson-friendly adapted payment object into the model's {@code Payment} object.
     *
     * @throws DukeException if there were any data constraints violated in the adapted person.
     */
    public Payment toModelType() throws DukeException {
        Payment.Builder paymentBuilder = new Payment.Builder();

        if (description == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "description"));
        }

        paymentBuilder.setDescription(description);

        if (receiver == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "receiver"));
        }

        paymentBuilder.setReceiver(receiver);

        if (due == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "due"));
        }

        paymentBuilder.setDue(due);

        if (remark == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "remark"));
        }

        paymentBuilder.setTag(remark);

        if (amount == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "amount"));
        }


        paymentBuilder.setAmount(amount);

        if (priority == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_STORAGE_MISSING_FIELD, "priority"));
        }

        paymentBuilder.setPriority(priority);

        return paymentBuilder.build();

    }

}
