package duke.ui.card;

import duke.data.Medicine;
import duke.data.Treatment;
import duke.exception.DukeFatalException;

import java.util.Arrays;
import java.util.List;

/**
 * A UI card that displays the basic information of a {@code Medicine}.
 */
public class MedicineCard extends TreatmentCard {
    private static final String FXML = "MedicineCard.fxml";
    private static final List<String> statuses = Arrays.asList("Not ordered", "In progress", "Completed");
    private final Medicine medicine;

    /**
     * Constructs an MedicineCard object with the specified {@code Medicine}'s details.
     *
     * @param medicine Medicine object.
     */
    public MedicineCard(Medicine medicine) throws DukeFatalException {
        super(FXML, medicine);

        this.medicine = medicine;
        fillMedicineCard();
    }

    /**
     * Fills up the UI card with the {@code Medicine}'s details.
     */
    private void fillMedicineCard() {
        nameLabel.setText(medicine.getName() + " - " + medicine.getDose());

        String statusText = String.valueOf(medicine.getStatusIdx());
        if (medicine.getStatusIdx() == Treatment.STATUSIDX_DEFAULT) {
            statusText += " - Default " + statuses.get(medicine.getStatusIdx());
        } else {
            statusText += " - " + medicine.getStatusStr();
        }
        statusLabel.setText(statusText);
    }

    @Override
    public Treatment getTreatment() {
        return medicine;
    }
}
