package duke.gui;

import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI component that displays information of a {@code Patient}.
 * TODO: Extend from UiComponent.
 */
public class PatientCard extends AnchorPane {
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/view/" + FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        this.patient = patient;
        nameLabel.setText(patient.getName());
        bedLabel.setText("Bed " + patient.getBedNo());

        if (patient.getPriDiagnosis() != null){
            diagnosisLabel.setText(patient.getPriDiagnosis().getDescription());
        } else {
            diagnosisLabel.setText("N.A.");
        }

        // TODO: Fetch issues
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
