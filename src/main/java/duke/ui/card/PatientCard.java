package duke.ui.card;

import duke.data.Patient;
import duke.exception.DukeFatalException;
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
     */
    public PatientCard(Patient patient) throws DukeFatalException {
        super(FXML);

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

        issueLabel.setText(patient.getCriticalCountStr());
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
        return patient == card.getPatient();
    }

    public Patient getPatient() {
        return patient;
    }
}
