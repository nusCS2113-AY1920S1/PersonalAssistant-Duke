package duke.ui.card;

import duke.data.Medicine;
import duke.data.Treatment;

/**
 * A UI card that displays the basic information of a {@code Medicine}.
 */
public class MedicineCard extends TreatmentCard {
    private static final String FXML = "MedicineCard.fxml";
    // TODO: holdover from when we intended to implement custom status arrays
    private static final String[] statuses = {"No status", "Request not submitted", "Submitted request",
        "Pending", "Ongoing", "Completed"};

    private final Medicine medicine;

    /**
     * Constructs an MedicineCard object with the specified {@code Medicine}'s details.
     *
     * @param medicine Medicine object.
     * @param index    Displayed index.
     */
    public MedicineCard(Medicine medicine, int index) {
        super(FXML, medicine, index);

        this.medicine = medicine;
        fillMedicineCard();
    }

    /**
     * Fills up the UI card with the {@code Medicine}'s details.
     */
    private void fillMedicineCard() {
        nameLabel.setText(medicine.getName() + " - " + medicine.getDose());

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
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof MedicineCard)) {
            return false;
        }

        MedicineCard card = (MedicineCard) object;
        return medicine.equals(card.getTreatment());
    }

    @Override
    public Treatment getTreatment() {
        return medicine;
    }
}
