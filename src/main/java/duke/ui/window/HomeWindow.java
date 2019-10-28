package duke.ui.window;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.data.Patient;
import duke.data.PatientMap;
import duke.ui.UiElement;
import duke.ui.card.PatientCard;
import duke.ui.card.UiCard;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * UI window for the Home context.
 */
public class HomeWindow extends UiElement<Region> {
    private static final String FXML = "HomeWindow.fxml";

    @FXML
    private JFXMasonryPane patientListPanel;
    @FXML
    private ScrollPane scrollPane;

    private PatientMap patientMap;
    private List<UiCard> patientCardList;

    /**
     * Constructs the Home UI window.
     *
     * @param patientMap PatientMap object.
     */
    public HomeWindow(PatientMap patientMap) {
        super(FXML, null);

        this.patientMap = patientMap;

        fillPatientListPanel();
        attachPatientListListener();

        JFXScrollPane.smoothScrolling(scrollPane);
    }

    /**
     * Fills {@code patientCardList} and {@code patientListPanel}.
     */
    private void fillPatientListPanel() {
        patientCardList = new ArrayList<>();

        for (Patient patient : patientMap.getPatientHashMap().values()) {
            int patientIndex = patientCardList.size() + 1;
            PatientCard patientCard = new PatientCard(patient, patientIndex);
            patientCardList.add(patientCard);
        }

        patientListPanel.getChildren().clear();
        patientListPanel.getChildren().addAll(patientCardList);
    }

    /**
     * Attaches a listener to the patient hashmap.
     * This listener updates the {@code patientListPanel} whenever the patient map is updated.
     */
    private void attachPatientListListener() {
        patientMap.getPatientObservableMap().addListener((MapChangeListener<String, Patient>) change -> {
            fillPatientListPanel();
        });
    }

    public List<UiCard> getPatientCardList() {
        return patientCardList;
    }
}
