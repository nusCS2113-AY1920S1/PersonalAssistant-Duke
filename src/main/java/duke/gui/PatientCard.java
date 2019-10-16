package duke.gui;

import duke.task.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiComponent<Region> {
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
     * Construct a PatientCard object with the specified patient's details.
     *
     * @param patient Patient object.
     */
    public PatientCard(Patient patient) {
        super(FXML);

        this.patient = patient;
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());
        diagnosisLabel.setText(patient.getPriDiagnosis().getDescription());
        // TODO: Get issues
        issueLabel.setText("No issues");
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PatientCard)) {
            return false;
        }

        PatientCard card = (PatientCard) obj;
        return patient.equals(card.getPatient());
    }
}
