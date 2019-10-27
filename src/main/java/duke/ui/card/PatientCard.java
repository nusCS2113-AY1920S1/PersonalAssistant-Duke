package duke.ui.card;

import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PatientCard extends UiCard {
    private static final String FXML = "PatientCard.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label bedLabel;
    @FXML
    private Label diagnosisLabel;
    @FXML
    private Label issueLabel;

    private final Patient patient;

    /**
     * An UI element that displays the basic information of a {@code patient}.
     */
    public PatientCard(Patient patient) {
        super(FXML);

        this.patient = patient;
        fillPatientCard();
    }

    /**
     * Fills up the UI card with the {@code patient}'s details.
     */
    private void fillPatientCard() {
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());

        if (patient.getPrimaryDiagnosis() != null) {
            diagnosisLabel.setText(patient.getPrimaryDiagnosis().getName());
        } else {
            diagnosisLabel.setText("No primary diagnosis");
        }

        // TODO: Fetch patient's critical issues
        issueLabel.setText("No issues");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof PatientCard)) {
            return false;
        }

        PatientCard card = (PatientCard) object;
        return patient.equals(card.getPatient());
    }

    public Patient getPatient() {
        return patient;
    }
}
