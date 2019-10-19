package duke.ui;

import duke.DukeCore;
import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI element that displays certain information of a {@code Patient}.
 * TODO: Extend from UiElement.
 */
class PatientCard extends AnchorPane {
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
     * Fills up the Patient Card with the {@code patient} details
     */
    private void initialisePatientCard() {
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());

        if (patient.getPriDiagnosis() != null) {
            diagnosisLabel.setText(patient.getPriDiagnosis().getDescription());
        } else {
            diagnosisLabel.setText("N.A.");
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
