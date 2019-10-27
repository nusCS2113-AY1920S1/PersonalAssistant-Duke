package duke.ui.window;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.data.Patient;
import duke.data.PatientMap;
import duke.ui.UiElement;
import duke.ui.card.PatientCard;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
     * Fills {@code patientListPanel}.
     */
    private void fillPatientListPanel() {
        List<Patient> patientList = new ArrayList<>(patientMap.getPatientHashMap().values());
        patientListPanel.getChildren().clear();

        ListIterator<Patient> iterator = patientList.listIterator();
        while (iterator.hasNext()) {
            PatientCard patientCard = new PatientCard(iterator.next(), iterator.nextIndex());
            patientListPanel.getChildren().add(patientCard);
        }
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
}
