package duke.ui;

import duke.DukeCore;
import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI element that displays basic information of a {@code Patient}.
 * TODO: Extend from UiElement.
 */
class PatientCard extends AnchorPane {
    private static final String FXML = "PatientCard.fxml";
    private final Patient patient;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bedLabel;
    @FXML
    private Label diagnosisLabel;
    @FXML
    private Label issueLabel;

    /**
     * Constructs a PatientCard object with the specified patient's details.
     *
     * @param patient Patient object.
     */
    PatientCard(Patient patient) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.patient = patient;

        initialisePatientCard();
    }

    /**
     * Fills up the Patient Card with the {@code patient} details.
     */
    private void initialisePatientCard() {
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());

        if (patient.getPrimaryDiagnosis() != null) {
            diagnosisLabel.setText(patient.getPrimaryDiagnosis().getName());
        } else {
            diagnosisLabel.setText("No primary diagnosis");
        }

        // TODO: Fetch patient's issues
        issueLabel.setText("No issues");
    }

    /**
     * {@inheritDoc}
     */
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

    public Patient getPatient() {
        return patient;
    }
}
