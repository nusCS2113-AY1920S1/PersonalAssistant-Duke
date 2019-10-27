package duke.ui.card;

import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A UI card that displays the basic information of a {@code Patient}.
 */
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
     * Constructs a PatientCard object with the specified {@code Patient}'s details.
     *
     * @param patient Patient object.
     * @param index   Displayed index.
     */
    public PatientCard(Patient patient, int index) {
        super(FXML, index);

        this.patient = patient;
        fillPatientCard();
    }

    /**
     * Fills up the UI card with the {@code Patient}'s details.
     */
    private void fillPatientCard() {
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());

        if (patient.getPrimaryDiagnosis() != null) {
            diagnosisLabel.setText(patient.getPrimaryDiagnosis().getName());
        } else {
            diagnosisLabel.setText("No primary diagnosis");
        }

        int critCount = patient.getCriticalCount();
        if (critCount == 0) {
            issueLabel.setText("No issues");
        } else if (critCount == 1) {
            issueLabel.setText("1 issue");
        } else {
            issueLabel.setText(critCount + "issues");
        }
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
