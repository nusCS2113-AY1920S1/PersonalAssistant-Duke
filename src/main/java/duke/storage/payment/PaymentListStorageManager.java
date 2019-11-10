package duke.storage.payment;

import static java.util.Objects.requireNonNull;

import duke.commons.FileUtil;
import duke.commons.JsonUtil;
import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.model.payment.PaymentList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access PaymentList data stored as a json file on the hard disk.
 */
public class PaymentListStorageManager implements PaymentListStorage {

    private static final Logger logger = LogsCenter.getLogger(PaymentListStorageManager.class);

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File PAYMENTS_FILE = new File(DEFAULT_USER_DIRECTORY, "payments.txt");
    private static final Path filePath = PAYMENTS_FILE.toPath();

    /**
     * Creates a {@code PaymentListStorageManager}.
     * Locates the file storing the PaymentList data.
     * If the file is not found, it will create a new file at the location.
     *
     * @throws IOException if errors occur when creating the file.
     */
    public PaymentListStorageManager() throws IOException {
        FileUtil.createIfMissing(filePath);
        logger.info("PaymentList.txt has been located.");
    }

    @Override
    public Optional<PaymentList> readPaymentList() {

        // Returns a new empty paymentList if the file is blank.
        if (PAYMENTS_FILE.length() == 0) {
            return Optional.of(new PaymentList());
        }

        Optional<JsonSerializablePaymentList> jsonPaymentList;
        try {
            jsonPaymentList = JsonUtil.readJsonFile(filePath, JsonSerializablePaymentList.class);
        } catch (DukeException e) {
            logger.warning("Json file has format errors!");
            return Optional.of(new PaymentList()); // Returns an empty paymentList as alternative.
        }

        if (jsonPaymentList.isEmpty()) {
            return Optional.of(new PaymentList()); // Returns an empty paymentList as alternative.
        }

        try {
            return Optional.of(jsonPaymentList.get().toModelType());
        } catch (DukeException e) {
            logger.warning("Illegal values found in " + filePath + ": " + e.getMessage());
            return Optional.of(new PaymentList()); // Returns an empty paymentList as alternative.
        }

    }

    @Override
    public void savePaymentList(PaymentList paymentList) throws IOException {
        requireNonNull(paymentList);
        JsonUtil.saveJsonFile(new JsonSerializablePaymentList(paymentList), filePath);
    }

}
