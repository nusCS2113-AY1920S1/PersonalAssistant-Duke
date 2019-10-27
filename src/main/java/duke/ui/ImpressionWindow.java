package duke.ui;

import com.jfoenix.controls.JFXListView;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.Treatment;
import duke.ui.card.EvidenceCard;
import duke.ui.card.InvestigationCard;
import duke.ui.card.MedicineCard;
import duke.ui.card.ObservationCard;
import duke.ui.card.PlanCard;
import duke.ui.card.ResultCard;
import duke.ui.card.TreatmentCard;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.Map;

/**
 * UI window for the Patient context.
 */
class ImpressionWindow extends UiElement<Region> {
    private static final String FXML = "ImpressionWindow.fxml";

    @FXML
    private Label nameLabel;
    @FXML
    private Label patientNameLabel;
    @FXML
    private Label patientBedLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label followUpLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label allergiesLabel;
    @FXML
    private JFXListView<EvidenceCard> evidenceListPanel;
    @FXML
    private JFXListView<TreatmentCard> treatmentListPanel;

    private Integer criticalCount = 0;
    private Integer followUpCount = 0;

    private Patient patient;
    private Impression impression;

    /**
     * Constructs the patient UI window.
     */
    ImpressionWindow(Impression impression, Patient patient) {
        super(FXML, null);
        if (impression != null && patient != null) {
            this.patient = patient;
            this.impression = impression;
            setImpression(impression, patient);
        }
    }

    public void setImpression(Impression impression, Patient patient) {
        assert (patient.getName().equals(impression.getParent().getName()));

        updateUi();

        patient.getAttributes().addListener((MapChangeListener<String, Object>) change -> {
            updateUi();
        });

        impression.getObservableEvidences().addListener((MapChangeListener<String, Evidence>) change -> {
            if (change.wasAdded() && newEvidenceCard(change.getValueAdded()) != null) {
                evidenceListPanel.getItems().add(newEvidenceCard(change.getValueAdded()));
                criticalCount += (change.getValueAdded().getPriority() == 1) ? 1 : 0;
            } else if (change.wasRemoved() && newEvidenceCard(change.getValueRemoved()) != null) {
                evidenceListPanel.getItems().remove(newEvidenceCard(change.getValueRemoved()));
                criticalCount -= (change.getValueRemoved().getPriority() == 1) ? 1 : 0;
            }
            criticalLabel.setText(criticalCount + " critical(s)");
        });

        impression.getObservableTreaments().addListener((MapChangeListener<String, Treatment>) change -> {
            if (change.wasAdded() && newTreatmentCard(change.getValueAdded()) != null) {
                treatmentListPanel.getItems().add(newTreatmentCard(change.getValueAdded()));
                criticalCount += (change.getValueAdded().getPriority() == 1) ? 1 : 0;
                followUpCount += (change.getValueAdded().getStatusIdx() < 5
                        && change.getValueAdded().getStatusIdx() >= 0) ? 1 : 0;
            } else if (change.wasRemoved() && newTreatmentCard(change.getValueAdded()) != null) {
                treatmentListPanel.getItems().remove(newTreatmentCard(change.getValueRemoved()));
                criticalCount -= (change.getValueRemoved().getPriority() == 1) ? 1 : 0;
                followUpCount -= (change.getValueRemoved().getStatusIdx() < 5
                        && change.getValueRemoved().getStatusIdx() >= 0) ? 1 : 0;
            }
            criticalLabel.setText(criticalCount + " critical(s)");
            followUpLabel.setText(followUpCount + " follow up(s)");
        });
    }

    /**
     * This function returns the new card added dependent on the class instance.
     * @param evidence the evidence
     * @return ObservationCard/ResultCard
     */
    private EvidenceCard newEvidenceCard(Evidence evidence) {
        if (evidence instanceof Observation) {
            // TODO: index
            return new ObservationCard((Observation) evidence, 0);
        } else if (evidence instanceof Result) {
            // TODO: index
            return new ResultCard((Result) evidence, 0);
        } else {
            return null;
        }
    }

    /**
     * This function returns the new card added dependent on the class instance.
     * @param treatment the treatment
     * @return InvestigationCard/MedicineCard/PlanCard
     */
    private TreatmentCard newTreatmentCard(Treatment treatment) {
        if (treatment instanceof Investigation) {
            // TODO: index
            return new InvestigationCard((Investigation) treatment, 0);
        } else if (treatment instanceof Medicine) {
            // TODO: index
            return new MedicineCard((Medicine) treatment, 0);
        } else if (treatment instanceof Plan) {
            // TODO: index
            return new PlanCard((Plan) treatment, 0);
        } else {
            return null;
        }
    }

    /**
     * This function update all labels in the Impression Window when called.
     * Todo May be replaced by listeners.
     */
    private void updateUi() {
        nameLabel.setText(String.valueOf(impression.getName()));
        patientNameLabel.setText(String.valueOf(patient.getName()));
        patientBedLabel.setText(String.valueOf(patient.getBedNo()));
        descriptionLabel.setText(String.valueOf(impression.getDescription()));

        StringBuilder allergies = new StringBuilder();
        if (patient.getAllergies() != null) {
            for (String allergy : patient.getAllergies().split(",")) {
                allergies.append(allergy.trim()).append(System.lineSeparator());
            }
            allergiesLabel.setText(allergies.toString());
        } else {
            allergiesLabel.setText("NIL");
        }

        evidenceListPanel.getItems().clear();
        treatmentListPanel.getItems().clear();
        criticalCount = 0;
        followUpCount = 0;

        for (Map.Entry<String, Evidence> pair : impression.getObservableEvidences().entrySet()) {
            evidenceListPanel.getItems().add(newEvidenceCard(pair.getValue()));
            criticalCount += (pair.getValue().getPriority() == 1) ? 1 : 0;
        }

        for (Map.Entry<String, Treatment> pair : impression.getObservableTreaments().entrySet()) {
            treatmentListPanel.getItems().add(newTreatmentCard(pair.getValue()));
            criticalCount += (pair.getValue().getPriority() == 1) ? 1 : 0;
            followUpCount += (pair.getValue().getStatusIdx() < 5 && pair.getValue().getStatusIdx() >= 0) ? 1 : 0;
        }

        criticalLabel.setText(criticalCount + " critical(s)");
        followUpLabel.setText(followUpCount + " follow up(s)");
    }
}
