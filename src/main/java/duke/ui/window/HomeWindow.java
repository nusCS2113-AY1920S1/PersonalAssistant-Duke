package duke.ui.window;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.data.DukeObject;
import duke.data.Patient;
import duke.ui.card.PatientCard;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

/**
 * UI window for the Home context.
 */
public class HomeWindow extends Window {
    private static final String FXML = "HomeWindow.fxml";

    @FXML
    private JFXMasonryPane patientListPanel;
    @FXML
    private ScrollPane scrollPane;

    private ObservableMap<String, Patient> patientObservableMap;
    private List<DukeObject> indexedPatientList;

    /**
     * Constructs the Home UI window.
     *
     * @param patientObservableMap ObservableMap of {@code Patient} objects.
     */
    public HomeWindow(ObservableMap<String, Patient> patientObservableMap) {
        super(FXML);
        this.patientObservableMap = patientObservableMap;
        JFXScrollPane.smoothScrolling(scrollPane);
        updateUi();
    }

    /**
     * Fills {@code indexedPatientList} and {@code patientListPanel}.
     */
    private void fillPatientList() {
        indexedPatientList = new ArrayList<>(patientObservableMap.values());

        patientListPanel.getChildren().clear();
        indexedPatientList.forEach(patient -> {
            PatientCard patientCard = new PatientCard((Patient) patient);
            patientCard.setIndex(indexedPatientList.indexOf(patient) + 1);
            patientListPanel.getChildren().add(patientCard);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUi() {
        fillPatientList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DukeObject> getIndexedList(String type) {
        return indexedPatientList;
    }

    /**
     * Attaches a listener to the patient hashmap.
     * This listener updates the {@code patientListPanel} whenever the patient hashmap is updated.
     */
    private void attachPatientListListener() {
        patientObservableMap.addListener((MapChangeListener<String, Patient>) change -> {
            fillPatientList();
        });
    }
}
