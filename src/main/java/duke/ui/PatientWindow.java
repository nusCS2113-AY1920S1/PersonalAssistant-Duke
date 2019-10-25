package duke.ui;

import duke.data.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * UI window for the Patient context.
 */
class PatientWindow extends UiElement<Region> {
    private static final String FXML = "PatientWindow.fxml";

    @FXML
    private Label name;
    @FXML
    private Label age;
    @FXML
    private Label height;
    @FXML
    private Label weight;
    @FXML
    private Label bed;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label history;

    /**
     * Constructs the patient UI window.
     */
    PatientWindow(Patient patient) {
        super(FXML, null);
        setPatient(patient);
    }

    public void setPatient(Patient patient) {
        if (patient == null) {
            return;
        }

        // TODO: Set default values if NULL.
        name.setText(String.valueOf(patient.getName()));
        bed.setText(String.valueOf(patient.getBedNo()));
        age.setText(String.valueOf(patient.getAge()));
        height.setText(String.valueOf(patient.getHeight()));
        weight.setText(String.valueOf(patient.getWeight()));
        phone.setText(String.valueOf(patient.getNumber()));
        address.setText(String.valueOf(patient.getAddress()));
        history.setText(String.valueOf(patient.getHistory()));
    }
}
