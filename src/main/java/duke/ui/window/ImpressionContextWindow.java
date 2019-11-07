package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.data.DukeObject;
import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.Treatment;
import duke.ui.UiStrings;
import duke.ui.card.EvidenceCard;
import duke.ui.card.InvestigationCard;
import duke.ui.card.MedicineCard;
import duke.ui.card.ObservationCard;
import duke.ui.card.PlanCard;
import duke.ui.card.ResultCard;
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

    private Patient patient;
    private Impression impression;

    /**
     * Constructs the patient UI window.
     */
    public ImpressionContextWindow(Impression impression, Patient patient) {
        super(FXML);

        if (impression != null && patient != null) {
            this.patient = patient;
            this.impression = impression;
            //setImpression();
            updateUi();
        }
    }

    /**
     * Set impressions for {@code patient}.
     */
    private void setImpression() {
        assert (patient.getName().equals(impression.getParent().getName()));

        updateUi();

        //patient.getAttributes().addListener((MapChangeListener<String, Object>) change -> {
        //    updateUi();
        //});

        // TODO: description doesn't update UI
        //patient.getImpressionsObservableMap().addListener((MapChangeListener<String, Impression>) change -> {
        //    updateUi();
        //});

        //        for (Map.Entry<String, Evidence> pair : impression.getObservableEvidences().entrySet()) {
        //            evidenceListPanel.getItems().add(newEvidenceCard(pair.getValue()));
        //
        //        }
        //
        //        for (Map.Entry<String, Treatment> pair : impression.getObservableTreatments().entrySet()) {
        //            treatmentListPanel.getItems().add(newTreatmentCard(pair.getValue()));
        //        }
        //
        //        // TODO: optimise by tracking critical and follow-up count in impression
        //        impression.getObservableEvidences().addListener((MapChangeListener<String, Evidence>) change -> {
        //            if (change.wasAdded() && newEvidenceCard(change.getValueAdded()) != null) {
        //                evidenceListPanel.getItems().add(newEvidenceCard(change.getValueAdded()));
        //            } else if (change.wasRemoved() && newEvidenceCard(change.getValueRemoved()) != null) {
        //                evidenceListPanel.getItems().remove(newEvidenceCard(change.getValueRemoved()));
        //            }
        //            criticalLabel.setText(impression.getCriticalCountStr());
        //        });
        //
        //        impression.getObservableTreatments().addListener((MapChangeListener<String, Treatment>) change -> {
        //            if (change.wasAdded() && newTreatmentCard(change.getValueAdded()) != null) {
        //                treatmentListPanel.getItems().add(newTreatmentCard(change.getValueAdded()));
        //            } else if (change.wasRemoved() && newTreatmentCard(change.getValueAdded()) != null) {
        //                treatmentListPanel.getItems().remove(newTreatmentCard(change.getValueRemoved()));
        //            }
        //            criticalLabel.setText(impression.getCriticalCountStr());
        //            followUpLabel.setText(impression.getFollowUpCountStr());
        //        });
    }

    /**
     * This function returns the new card added dependent on the class instance.
     *
     * @param evidence Evidence object
     * @param index    Displayed index.
     * @return ObservationCard/ResultCard
     */
    private EvidenceCard newEvidenceCard(Evidence evidence, int index) {
        EvidenceCard evidenceCard;

        if (evidence instanceof Observation) {
            evidenceCard = new ObservationCard((Observation) evidence);
        } else if (evidence instanceof Result) {
            evidenceCard = new ResultCard((Result) evidence);
        } else {
            return null;
        }

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
    private TreatmentCard newTreatmentCard(Treatment treatment, int index) {
        TreatmentCard treatmentCard;

        if (treatment instanceof Investigation) {
            treatmentCard = new InvestigationCard((Investigation) treatment);
        } else if (treatment instanceof Medicine) {
            treatmentCard = new MedicineCard((Medicine) treatment);
        } else if (treatment instanceof Plan) {
            treatmentCard = new PlanCard((Plan) treatment);
        } else {
            return null;
        }

        treatmentCard.setIndex(index);
        return treatmentCard;
    }

    /**
     * {@inheritDoc}
     */
    public void updateUi() {
        nameLabel.setText(String.valueOf(impression.getName()));
        patientNameLabel.setText(String.valueOf(patient.getName()));
        patientBedLabel.setText(String.valueOf(patient.getBedNo()));
        descriptionLabel.setText(String.valueOf(impression.getDescription()));

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
