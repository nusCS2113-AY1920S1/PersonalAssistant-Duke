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
                    // TODO: index
                    impressionsListPanel.getItems().add(0,
                            new ImpressionCard(change.getValueAdded(), true, 0));
                } else {
                    // TODO: index
                    impressionsListPanel.getItems().add(new ImpressionCard(change.getValueAdded(), false, 0));
                }
            } else if (change.wasRemoved()) {
                // TODO: index
                impressionsListPanel.getItems().remove(new ImpressionCard(change.getValueRemoved(), false, 0));
            }
        });
    }

    private void updateUi() {
        name.setText(String.valueOf(patient.getName()));
        bed.setText(String.valueOf(patient.getBedNo()));
        int ageNum = patient.getAge();
        age.setText((ageNum == -1) ? "No age set" : ageNum + " years old");
        int heightNum = patient.getHeight();
        height.setText((heightNum == -1) ? "No height set" : patient.getHeight() + " cm");
        int weightNum = patient.getWeight();
        weight.setText((weightNum == -1) ? "No weight set" : patient.getWeight() + " kg");
        int number = patient.getNumber();
        phone.setText((number == -1) ? "No number set" : String.valueOf(number));
        String addressStr = patient.getAddress();
        address.setText(("".equals(addressStr)) ? "No address given" : addressStr);
        String historyStr = patient.getHistory();
        history.setText(("".equals(historyStr)) ? "No history provided" : historyStr);

        StringBuilder allergies = new StringBuilder();
        if (patient.getAllergies() != null) {
            //TODO document the fact that comma separated allergies are displayed on distinct rows
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.strip()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        } else {
            allergiesLabel.setText(Strings.DISPLAY_ALLERGIES_NONE);
        }

        impressionsListPanel.getItems().clear();
        for (Map.Entry<String, Impression> pair : patient.getImpressionsObservableMap().entrySet()) {
            Impression primaryImpression = null;

            if (pair.getValue().equals(patient.getPrimaryDiagnosis())) {
                primaryImpression = pair.getValue();
            } else {
                // TODO: index
                impressionsListPanel.getItems().add(new ImpressionCard(pair.getValue(), false, 0));
            }

            if (primaryImpression != null) {
                // TODO: index
                impressionsListPanel.getItems().add(0, new ImpressionCard(primaryImpression, true, 0));
            }
        }
    }
}
