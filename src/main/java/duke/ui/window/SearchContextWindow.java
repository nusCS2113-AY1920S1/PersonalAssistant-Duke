package duke.ui.window;

import com.jfoenix.controls.JFXListView;
import duke.DukeCore;
import duke.data.DukeObject;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.SearchResult;
import duke.ui.UiStrings;
import duke.ui.card.ImpressionCard;
import duke.ui.card.InvestigationCard;
import duke.ui.card.MedicineCard;
import duke.ui.card.ObservationCard;
import duke.ui.card.PatientCard;
import duke.ui.card.PlanCard;
import duke.ui.card.ResultCard;
import duke.ui.card.UiCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.List;

/**
 * UI window for the Patient context.
 */
class SearchContextWindow extends ContextWindow {
    private static final String FXML = "SearchContextWindow.fxml";

    @FXML
    private Label parentTypeLabel;
    @FXML
    private Label parentNameLabel;
    @FXML
    private Label searchTermLabel;
    @FXML
    private Label searchDetailsLabel;
    @FXML
    private JFXListView<UiCard> searchListPanel;
    @FXML
    private ImageView parentTypeImage2;
    @FXML
    private ImageView parentTypeImage;

    private DukeObject parent;
    private SearchResult searchResults;

    /**
     * Constructs the search UI window.
     */
    SearchContextWindow(SearchResult searchResults) {
        super(FXML);

        if (searchResults != null) {
            this.parent = searchResults.getParent();
            this.searchResults = searchResults;
            setSearchWindow();
        }
    }

    private void setSearchWindow() {
        setParent();
        searchTermLabel.setText(searchResults.getName());
        searchDetailsLabel.setText(buildSearchDetails());
        for (Pair<DukeObject, Class<? extends DukeObject>> entry : searchResults.getSearchList()) {
            searchListPanel.getItems().add(newCard(entry.getKey()));
        }
    }

    /**
     * This function returns the new card added dependent on the class instance.
     * @param object the object
     * @return relevant card
     */
    private UiCard newCard(DukeObject object) {
        if (object instanceof Patient) {
            return new PatientCard((Patient) object);
        } else if (object instanceof Impression) {
            return new ImpressionCard((Impression) object,false);
        } else if (object instanceof Observation) {
            // TODO: index
            return new ObservationCard((Observation) object);
        } else if (object instanceof Result) {
            // TODO: index
            return new ResultCard((Result) object);
        } else if (object instanceof Investigation) {
            // TODO: index
            return new InvestigationCard((Investigation) object);
        } else if (object instanceof Medicine) {
            // TODO: index
            return new MedicineCard((Medicine) object);
        } else if (object instanceof Plan) {
            // TODO: index
            return new PlanCard((Plan) object);
        } else {
            return null;
        }
    }

    private void setParent() {
        if (parent != null) {
            parentTypeLabel.setText(parent.getClass().getSimpleName().toUpperCase() + " SEARCH CONTEXT");
            parentNameLabel.setText(String.valueOf(parent.getName()));
            Image image = new Image(DukeCore.class.getResourceAsStream(
                    UiStrings.SEARCH_HELPER.get(parent.getClass().getSimpleName())));
            parentTypeImage.setImage(image);
            parentTypeImage2.setImage(image);
        }
    }

    private String buildSearchDetails() {
        StringBuilder searchDetails = new StringBuilder();
        searchDetails.append("There are ").append(searchResults.getSearchList().size()).append(" result(s).");
        searchDetails.append(System.lineSeparator());
        if (parent == null) {
            searchDetails.append("There are ").append(searchResults.getPatientList().size()).append(" patient(s)");
            searchDetails.append(System.lineSeparator());
        }
        if (parent instanceof Patient || parent == null) {
            searchDetails.append("There are ")
                    .append(searchResults.getImpressionList().size()).append(" impression(s)");
            searchDetails.append(System.lineSeparator());
        }
        searchDetails.append("There are ").append(searchResults.getEvidenceList().size()).append(" evidence(s)");
        searchDetails.append(System.lineSeparator());
        searchDetails.append("There are ").append(searchResults.getTreatmentList().size()).append(" treatment(s)");
        return searchDetails.toString();
    }

    @Override
    public void updateUi() {
        // TODO
    }

    @Override
    public List<DukeObject> getIndexedList(String type) {
        return null;
    }
}
