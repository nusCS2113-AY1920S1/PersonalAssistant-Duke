package duke.ui.window;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.data.Patient;
import duke.ui.UiElement;
import duke.ui.card.PatientCard;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * UI window for the Home context.
 */
public class HomeWindow extends UiElement<Region> {
    private static final String FXML = "HomeWindow.fxml";

    @FXML
    private JFXMasonryPane patientListPanel;
    @FXML
    private ScrollPane scrollPane;

    private ObservableMap<String, Patient> patientObservableMap;

    /**
     * Constructs the Home UI window.
     *
     * @param patientObservableMap ObservableMap of {@code Patient} objects.
     */
    public HomeWindow(ObservableMap<String, Patient> patientObservableMap) {
        super(FXML, null);

        this.patientObservableMap = patientObservableMap;

        fillPatientListPanel();
        attachPatientListListener();

        JFXScrollPane.smoothScrolling(scrollPane);
    }

    /**
     * Fills {@code patientListPanel}.
     */
    private void fillPatientListPanel() {
        for (Patient patient : patientObservableMap.values()) {
            PatientCard patientCard = new PatientCard(patient);
            patientCard.setIndex(patientListPanel.getChildren().size() + 1);
            patientListPanel.getChildren().add(patientCard);
        }
    }

    /**
     * Attaches a listener to the patient hashmap.
     * This listener updates the {@code patientListPanel} whenever the patient hashmap is updated.
     */
    private void attachPatientListListener() {
        patientObservableMap.addListener((MapChangeListener<String, Patient>) change -> {
            if (change.wasAdded()) {
                PatientCard patientCard = new PatientCard(change.getValueAdded());
                patientListPanel.getChildren().add(patientCard);
            } else if (change.wasRemoved()) {
                patientListPanel.getChildren().remove(new PatientCard(change.getValueRemoved()));
            }

            patientListPanel.getChildren().forEach(node -> {
                PatientCard card = (PatientCard) node;
                card.setIndex(patientListPanel.getChildren().indexOf(card) + 1);
            });
        });
    }

    public ObservableList<Node> getPatientCardList() {
        return patientListPanel.getChildrenUnmodifiable();
    }
}
