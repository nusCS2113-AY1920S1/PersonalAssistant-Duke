package duke.ui;

import duke.DukeCore;
import duke.data.Medicine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class MedicineCard extends TreatmentCard {
    private static final String FXML = "MedicineCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label statusLabel;

    private Medicine medicine;

    /**
     * Constructs an MedicineCard object with the specified medicine's details.
     *
     * @param medicine Medicine object.
     */
    MedicineCard(Medicine medicine) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.medicine = medicine;
        nameLabel.setText(medicine.getName() + " - " + medicine.getDose());

        final String[] priorities = {"No Priority set", "Critical", "Urgent", "Compulsory", "Optional"};
        String priorityText = String.valueOf(medicine.getPriority());
        if (medicine.getPriority() >= 0 && medicine.getPriority() < priorities.length) {
            priorityText += " - " + priorities[medicine.getPriority()];
        }

        criticalLabel.setText(priorityText);

        // TODO: holdover from when we intended to implement custom status arrays
        final String[] statuses = {"No Status set", "Request not submitted", "Submitted request",
                                   "Pending", "Ongoing", "Completed"};
        String statusText = String.valueOf(medicine.getStatusIdx());
        if (medicine.getStatusIdx() >= 0
            && medicine.getStatusIdx() < Medicine.getStatusArr().size()) {
            statusText += " - " + medicine.getStatusStr();
        } else if (medicine.getStatusIdx() >= 0 && medicine.getStatusIdx() < statuses.length) {
            statusText += " - Default " + statuses[medicine.getStatusIdx()];
        }
        statusLabel.setText(statusText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof MedicineCard)) {
            return false;
        }

        MedicineCard card = (MedicineCard) obj;
        return medicine.equals(card.getMedicine());
    }

    public Medicine getMedicine() {
        return medicine;
    }
}
