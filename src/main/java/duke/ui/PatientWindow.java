package duke.ui;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import duke.data.Impression;
import duke.data.Patient;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.Map;

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
    @FXML
    private JFXMasonryPane allergiesListPanel;
    @FXML
    private JFXListView<ImpressionCard> impressionsListPanel;

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

        for (Map.Entry<String, Impression> pair : patient.getImpressionsObservableMap().entrySet()) {
            impressionsListPanel.getItems().add(new ImpressionCard(pair.getValue()));
        }

        patient.getImpressionsObservableMap().addListener((MapChangeListener<String, Impression>) change -> {
            if (change.wasAdded()) {
                impressionsListPanel.getItems().add(new ImpressionCard(change.getValueAdded()));
            } else if (change.wasRemoved()) {
                impressionsListPanel.getItems().remove(new ImpressionCard(change.getValueRemoved()));
            }
        });
    }
}
