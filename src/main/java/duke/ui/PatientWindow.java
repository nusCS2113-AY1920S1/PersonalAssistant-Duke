package duke.ui;

import com.jfoenix.controls.JFXListView;
import duke.data.Impression;
import duke.data.Patient;
import duke.ui.card.ImpressionCard;
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
    private Label allergiesLabel;
    @FXML
    private JFXListView<ImpressionCard> impressionsListPanel;

    private Patient patient;

    /**
     * Constructs the patient UI window.
     */
    PatientWindow(Patient patient) {
        super(FXML, null);

        this.patient = patient;
        setPatient();
    }

    private void setPatient() {
        if (patient == null) {
            return;
        }

        updateUi();

        patient.getAttributes().addListener((MapChangeListener<String, Object>) change -> {
            updateUi();
        });

        patient.getImpressionsObservableMap().addListener((MapChangeListener<String, Impression>) change -> {
            if (change.wasAdded()) {
                if (change.getValueAdded().equals(patient.getPrimaryDiagnosis())) {
                    impressionsListPanel.getItems().add(0, new ImpressionCard(change.getValueAdded(), true));
                } else {
                    impressionsListPanel.getItems().add(new ImpressionCard(change.getValueAdded(), false));
                }
            } else if (change.wasRemoved()) {
                impressionsListPanel.getItems().remove(new ImpressionCard(change.getValueRemoved(), false));
            }
        });
    }

    private void updateUi() {
        // TODO: Set default values if NULL.
        name.setText(String.valueOf(patient.getName()));
        bed.setText(String.valueOf(patient.getBedNo()));
        age.setText(String.valueOf(patient.getAge()) + " years old");
        height.setText(String.valueOf(patient.getHeight()) + " cm");
        weight.setText(String.valueOf(patient.getWeight())  + " kg");
        phone.setText(String.valueOf(patient.getNumber()));
        address.setText(String.valueOf(patient.getAddress()));
        history.setText(String.valueOf(patient.getHistory()));

        StringBuilder allergies = new StringBuilder();
        if (patient.getAllergies() != null) {
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.trim()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        } else {
            allergiesLabel.setText("NIL");
        }

        impressionsListPanel.getItems().clear();
        for (Map.Entry<String, Impression> pair : patient.getImpressionsObservableMap().entrySet()) {
            Impression primaryImpression = null;

            if (pair.getValue().equals(patient.getPrimaryDiagnosis())) {
                primaryImpression = pair.getValue();
            } else {
                impressionsListPanel.getItems().add(new ImpressionCard(pair.getValue(), false));
            }

            if (primaryImpression != null) {
                impressionsListPanel.getItems().add(0, new ImpressionCard(primaryImpression, true));
            }
        }
    }
}
