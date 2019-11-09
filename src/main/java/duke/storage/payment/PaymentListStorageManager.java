package duke.storage.payment;

import static java.util.Objects.requireNonNull;

import duke.commons.FileUtil;
import duke.commons.JsonUtil;
import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.model.payment.Payment;
import duke.model.payment.PaymentList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

public class PaymentListStorageManager implements PaymentListStorage {

    private static final Logger logger = LogsCenter.getLogger(PaymentListStorageManager.class);

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File PAYMENTS_FILE = new File(DEFAULT_USER_DIRECTORY, "payments.txt");
    private static final Path filePath = PAYMENTS_FILE.toPath();

    public PaymentListStorageManager() throws IOException {
        FileUtil.createIfMissing(filePath);
        logger.info("PaymentList.txt file created");
    }

    @Override
    public Optional<PaymentList> readPaymentList() throws DukeException {

        logger.info("Entered the JsonStorageManager");

        if (PAYMENTS_FILE.length() == 0) {
            return Optional.of(new PaymentList(new ArrayList<Payment>()));
        }

        Optional<JsonSerializablePaymentList> jsonPaymentList = JsonUtil.readJsonFile(
                filePath, JsonSerializablePaymentList.class);
        logger.info("Json file is read");
        if (!jsonPaymentList.isPresent()) {
            return Optional.empty();
        }

        try {
            logger.info("returning the paymentList from storage");
            return Optional.of(jsonPaymentList.get().toModelType());
        } catch (DukeException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DukeException(e.getMessage());
        }


    }

    @Override
    public void savePaymentList(PaymentList paymentList) throws IOException {
        requireNonNull(paymentList);
        JsonUtil.saveJsonFile(new JsonSerializablePaymentList(paymentList), filePath);
    }


}
