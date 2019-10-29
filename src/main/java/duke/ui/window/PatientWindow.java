package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.Impression;
import duke.data.Patient;
import duke.ui.UiElement;
import duke.ui.UiStrings;
import duke.ui.card.ImpressionCard;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * UI window for the Patient context.
 */
public class PatientWindow extends UiElement<Region> {
    private static final String FXML = "PatientWindow.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label bedLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label historyLabel;
    @FXML
    private Label allergiesLabel;
    @FXML
    private JFXListView<Node> impressionsListPanel;

    private Patient patient;
    private CommandWindow window;

    /**
     * Constructs the patient UI window.
     */
    public PatientWindow(Patient patient, CommandWindow window) {
        super(FXML, null);

        this.patient = patient;
        this.window = window;

        if (patient == null) {
            return;
        }

        updatePatientWindow();
        attachPatientWindowListener();
    }

    private void updatePatientWindow() {
        nameLabel.setText(String.valueOf(patient.getName()));
        bedLabel.setText(String.valueOf(patient.getBedNo()));
        int ageNum = patient.getAge();
        ageLabel.setText((ageNum == -1) ? UiStrings.DISPLAY_AGE_NOT_SET : ageNum + " years old");
        int heightNum = patient.getHeight();
        heightLabel.setText((heightNum == -1) ? UiStrings.DISPLAY_HEIGHT_NOT_SET : patient.getHeight() + " cm");
        int weightNum = patient.getWeight();
        weightLabel.setText((weightNum == -1) ? UiStrings.DISPLAY_WEIGHT_NOT_SET : patient.getWeight() + " kg");
        int number = patient.getNumber();
        phoneLabel.setText((number == -1) ? UiStrings.DISPLAY_NUMBER_NOT_SET : String.valueOf(number));
        String addressStr = patient.getAddress();
        addressLabel.setText(("".equals(addressStr)) ? UiStrings.DISPLAY_ADDRESS_NOT_SET : addressStr);
        String historyStr = patient.getHistory();
        historyLabel.setText(("".equals(historyStr)) ? UiStrings.DISPLAY_HISTORY_NOT_SET : historyStr);

        StringBuilder allergies = new StringBuilder();
        if ("".equals(patient.getAllergies())) {
            //TODO document the fact that comma separated allergies are displayed on distinct rows
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.strip()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        } else {
            allergiesLabel.setText(UiStrings.DISPLAY_ALLERGIES_NONE);
        }

        impressionsListPanel.getItems().clear();
        for (Impression impression : patient.getImpressionsObservableMap().values()) {
            ImpressionCard impressionCard;

            if (impression.equals(patient.getPrimaryDiagnosis())) {
                impressionCard = new ImpressionCard(impression, true);
                impressionsListPanel.getItems().add(0, impressionCard);
            } else {
                impressionCard = new ImpressionCard(impression, false);
                impressionsListPanel.getItems().add(impressionCard);
            }
        }

        impressionsListPanel.getItems().forEach(card -> {
            ((ImpressionCard) card).setIndex(impressionsListPanel.getItems().indexOf(card) + 1);
        });
    }

    private void attachPatientWindowListener() {
        patient.getAttributes().addListener((MapChangeListener<String, Object>) change -> {
            updatePatientWindow();
        });
    }

    /**
     * Retrieves list of UI cards in the Impression context.
     *
     * @return List of UI cards.
     */
    public ObservableList<Node> getCardList() {
        // TODO: Bug. Size 0 when called by commands
        return impressionsListPanel.getItems();
    }
}
