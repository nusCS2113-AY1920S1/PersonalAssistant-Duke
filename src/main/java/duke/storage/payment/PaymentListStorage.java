package duke.storage.payment;

import duke.model.payment.PaymentList;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for duke.model.payment.PaymentList.
 */
public interface PaymentListStorage {

    /**
     * Returns PaymentList data as a PaymentList.
     *
     * Returns {@code Optional.empty()} if storage file is not found,
     * if the data in storage is not in the expected format, or
     * if there was any problem when reading from the storage.
     */
    Optional<PaymentList> readPaymentList();

    /**
     * Saves the given PaymentList to the storage.
     *
     * @param paymentList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePaymentList(PaymentList paymentList) throws IOException;

}
