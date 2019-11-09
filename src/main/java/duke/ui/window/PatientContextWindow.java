package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiStrings;
import duke.ui.card.ImpressionCard;
import duke.ui.card.TreatmentCard;
import duke.ui.card.UiCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

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
    private JFXListView<TreatmentCard> investigationListPanel;

    private Patient patient;
    private List<DukeObject> indexedImpressionList;
    private List<DukeObject> indexedCriticalList;
    private List<DukeObject> indexedInvestigationList;

    /**
     * Constructs the patient UI window.
     */
    public PatientContextWindow(Patient patient) throws DukeFatalException {
        super(FXML);

        this.patient = patient;
        this.indexedImpressionList = new ArrayList<>();
        this.indexedCriticalList = new ArrayList<>();
        this.indexedInvestigationList = new ArrayList<>();

        if (patient == null) {
            return;
        }

        updateUi();
    }

    private void updatePatientWindow() throws DukeFatalException {
        fillPatientDetails();
        clearLists();
        fillLists();
        indexLists();
    }

    private void fillPatientDetails() {
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
            allergiesLabel.setText(UiStrings.DISPLAY_ALLERGIES_NONE);
        } else {
            // TODO: document the fact that comma separated allergies are displayed on distinct rows
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.strip()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        }
    }

    private void clearLists() {
        indexedImpressionList.clear();
        indexedCriticalList.clear();
        indexedInvestigationList.clear();
        impressionListPanel.getItems().clear();
        criticalListPanel.getItems().clear();
        indexedInvestigationList.clear();
    }

    private void indexLists() {
        impressionListPanel.getItems().forEach(card -> {
            card.setIndex(impressionListPanel.getItems().indexOf(card) + 1);
        });

        criticalListPanel.getItems().forEach(card -> {
            card.setIndex(criticalListPanel.getItems().indexOf(card) + 1);
        });

        investigationListPanel.getItems().forEach(card -> {
            card.setIndex(investigationListPanel.getItems().indexOf(card) + 1);
        });
    }

    private void fillLists() throws DukeFatalException {
        for (Impression impression : patient.getImpressionList()) {
            // Impression list
            ImpressionCard impressionCard;
            if (patient.getPrimaryDiagnosis() != null && impression.equals(patient.getPrimaryDiagnosis())) {
                impressionCard = new ImpressionCard(impression, true);
                impressionListPanel.getItems().add(0, impressionCard);
                indexedImpressionList.add(0, impression);
            } else {
                impressionCard = new ImpressionCard(impression, false);
                impressionListPanel.getItems().add(impressionCard);
                indexedImpressionList.add(impression);
            }

            // Critical list
            for (Treatment treatment : impression.getTreatments()) {
                if (treatment.getPriority() == 1) {
                    criticalListPanel.getItems().add(treatment.toCard());
                    indexedCriticalList.add(treatment);
                }
            }

            for (Evidence evidence : impression.getEvidences()) {
                if (evidence.getPriority() == 1) {
                    criticalListPanel.getItems().add(evidence.toCard());
                    indexedCriticalList.add(evidence);
                }
            }

            // Investigation list
            for (Treatment treatment : impression.getTreatments()) {
                if (treatment instanceof Investigation && treatment.getPriority() != 1) {
                    // only display investigations not seen in criticals
                    investigationListPanel.getItems().add(treatment.toCard());
                    indexedInvestigationList.add(treatment);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateUi() throws DukeFatalException {
        updatePatientWindow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DukeObject> getIndexedList(String type) {
        if ("impression".equals(type)) {
            return indexedImpressionList;
        } else if ("critical".equals(type)) {
            return indexedCriticalList;
        } else if ("investigation".equals(type)) {
            return indexedInvestigationList;
        } else {
            return null;
        }
    }
}
