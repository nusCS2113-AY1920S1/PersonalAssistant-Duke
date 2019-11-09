package duke.ui;

import duke.model.payment.Payment;
import duke.model.payment.Payment.Priority;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentBox extends UiPart<AnchorPane> {

    private static final String FXML_FILE_NAME = "PaymentBox.fxml";

    private static final String PRIORITY_PREFIX = "Priority.";

    private final Payment payment;

    @FXML
    private Label indexLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label receiverLabel;

    @FXML
    private Label dueLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label tagLabel;

    @FXML
    private Label overdueLabel;

    /**
     *
     *
     * @param payment
     * @param displayedIndex
     */
    public PaymentBox(Payment payment, int displayedIndex) {
        super(FXML_FILE_NAME, null);
        this.payment = payment;

        indexLabel.setText(displayedIndex + ". ");
        amountLabel.setText(payment.getAmount().toString() + " SGD");
        receiverLabel.setText(payment.getReceiver());
        String due = payment.getDue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dueLabel.setText(due);
        descriptionLabel.setText(payment.getDescription());

        Priority priority = payment.getPriority();
        BackgroundFill backgroundFill;
        switch (priority) {
        case HIGH:
            backgroundFill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
            break;
        case MEDIUM:
            backgroundFill = new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY);
            break;
        case LOW:
            backgroundFill = new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY);
            break;
        default:
            backgroundFill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY);
        }
        priorityLabel.setBackground(new Background(backgroundFill));
        priorityLabel.setText(PRIORITY_PREFIX + priority.toString());
        tagLabel.setText(payment.getTag());
        if (payment.getDue().isBefore(LocalDate.now())) {
            overdueLabel.setVisible(true);
        }
    }



}
