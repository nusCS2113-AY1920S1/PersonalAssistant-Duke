package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Patient;
import duke.data.Treatment;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiStrings;
import duke.ui.card.EvidenceCard;
import duke.ui.card.TreatmentCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

/**
 * UI window for the Impression context.
 */
public class ImpressionContextWindow extends ContextWindow {
    private static final String FXML = "ImpressionContextWindow.fxml";

    @FXML
    private Label impressionNameLabel;
    @FXML
    private Label patientNameLabel;
    @FXML
    private Label patientBedLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label followUpLabel;
    @FXML
    private Label impressionDescriptionLabel;
    @FXML
    private Label allergiesLabel;
    @FXML
    private JFXListView<EvidenceCard> evidenceListPanel;
    @FXML
    private JFXListView<TreatmentCard> treatmentListPanel;

    private Patient patient;
    private Impression impression;

    /**
     * Constructs the patient UI window.
     */
    public ImpressionContextWindow(Impression impression, Patient patient) throws DukeFatalException {
        super(FXML);

        if (impression != null && patient != null) {
            this.patient = patient;
            this.impression = impression;
            assert (patient.getName().equals(impression.getParent().getName()));
            updateUi();
        }
    }

    /**
     * This function returns the new card added dependent on the class instance.
     *
     * @param evidence Evidence object
     * @param index    Displayed index.
     * @return ObservationCard/ResultCard
     */
    private EvidenceCard newEvidenceCard(Evidence evidence, int index) throws DukeFatalException {
        EvidenceCard evidenceCard = evidence.toCard();
        evidenceCard.setIndex(index);
        return evidenceCard;
    }

    /**
     * This function returns the new card added dependent on the class instance.
     *
     * @param treatment Treatment object.
     * @param index     Displayed index.
     * @return InvestigationCard/MedicineCard/PlanCard
     */
    private TreatmentCard newTreatmentCard(Treatment treatment, int index) throws DukeFatalException {
        TreatmentCard treatmentCard = treatment.toCard();
        treatmentCard.setIndex(index);
        return treatmentCard;
    }

    /**
     * {@inheritDoc}
     */
    public void updateUi() throws DukeFatalException {
        impressionNameLabel.setText(String.valueOf(impression.getName()));
        patientNameLabel.setText(String.valueOf(patient.getName()));
        patientBedLabel.setText(String.valueOf(patient.getBedNo()));
        impressionDescriptionLabel.setText(String.valueOf(impression.getDescription()));

        StringBuilder allergies = new StringBuilder();
        if (patient.getAllergies() != null) {
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.strip()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        } else {
            allergiesLabel.setText(UiStrings.DISPLAY_ALLERGIES_NONE);
        }

        criticalLabel.setText(impression.getCriticalCountStr());
        followUpLabel.setText(impression.getFollowUpCountStr());

        evidenceListPanel.getItems().clear();
        for (Evidence evidence : impression.getEvidences()) {
            int index = (evidence.getPriority() == 1) ? 1 : evidenceListPanel.getItems().size() + 1;
            evidenceListPanel.getItems().add(newEvidenceCard(evidence, index));
        }

        treatmentListPanel.getItems().clear();
        for (Treatment treatment : impression.getTreatments()) {
            int index = (treatment.getPriority() == 1) ? 1 : treatmentListPanel.getItems().size() + 1;
            treatmentListPanel.getItems().add(newTreatmentCard(treatment, index));
        }
    }

    @Override
    public List<DukeObject> getIndexedList(String type) {
        return null;
    }
}
