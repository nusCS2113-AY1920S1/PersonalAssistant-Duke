package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.DukeData;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeFatalException;
import duke.ui.card.ImpressionCard;
import duke.ui.card.TreatmentCard;
import duke.ui.card.UiCard;
import duke.ui.commons.UiStrings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

//@@author gowgos5

/**
 * UI window for the Patient context.
 */
public class PatientContextWindow extends ContextWindow {
    private static final String FXML = "PatientContextWindow.fxml";

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
    private JFXListView<ImpressionCard> impressionListPanel;
    @FXML
    private JFXListView<UiCard> criticalListPanel;
    @FXML
    private JFXListView<TreatmentCard> followUpListPanel;

    private Patient patient;

    /**
     * Constructs the patient UI window.
     */
    public PatientContextWindow(Patient patient) throws DukeFatalException {
        super(FXML);

        if (patient == null) {
            throw new DukeFatalException(UiStrings.MESSAGE_ERROR_UNINITIALISED_PATIENT);
        }

        this.patient = patient;

        updateUi();
    }

    private void updatePatientWindow() throws DukeFatalException {
        fillPatientDetails();
        clearLists();
        fillLists();
        indexLists();
    }

    /**
     * Fills {@code patient}'s biometric details and particulars.
     */
    private void fillPatientDetails() {
        nameLabel.setText(patient.getName());

        bedLabel.setText(patient.getBedNo());

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
            allergiesLabel.setText(UiStrings.DISPLAY_ALLERGIES_NONE);
        } else {
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.strip()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        }
    }


    /**
     * Clears all card and UI lists.
     */
    private void clearLists() {
        impressionListPanel.getItems().clear();
        criticalListPanel.getItems().clear();
        followUpListPanel.getItems().clear();
    }

    /**
     * Fills all UI lists in this window.
     */
    private void indexLists() {
        impressionListPanel.getItems().forEach(card -> {
            card.setIndex(impressionListPanel.getItems().indexOf(card) + 1);
        });

        criticalListPanel.getItems().forEach(card -> {
            card.setIndex(criticalListPanel.getItems().indexOf(card) + 1);
        });

        followUpListPanel.getItems().forEach(card -> {
            card.setIndex(followUpListPanel.getItems().indexOf(card) + 1);
        });
    }

    private void fillLists() throws DukeFatalException {
        for (Impression impression : patient.getImpressionList()) {
            // Impression list
            ImpressionCard impressionCard;
            if (patient.getPrimaryDiagnosis() != null && impression.equals(patient.getPrimaryDiagnosis())) {
                impressionCard = new ImpressionCard(impression, true);
                impressionListPanel.getItems().add(0, impressionCard);
            } else {
                impressionCard = new ImpressionCard(impression, false);
                impressionListPanel.getItems().add(impressionCard);
            }
        }

        for (DukeData criticalData : patient.getCriticalList()) {
            criticalListPanel.getItems().add(criticalData.toCard());
        }

        for (Treatment followUp : patient.getFollowUpList()) {
            followUpListPanel.getItems().add(followUp.toCard());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateUi() throws DukeFatalException {
        updatePatientWindow();
    }
}
