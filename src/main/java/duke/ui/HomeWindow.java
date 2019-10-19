package duke.ui;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.DukeCore;
import duke.data.Patient;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.Map;

/**
 * UI window for the Home context.
 */
class HomeWindow extends UiElement<Region> {
    private static final String FXML = "HomeWindow.fxml";

    @FXML
    private JFXMasonryPane patientListPanel;
    @FXML
    private ScrollPane scrollPane;

    private final DukeCore core;

    /**
     * Constructs the Home UI window.
     */
    HomeWindow(DukeCore core) {
        super(FXML, null);

        this.core = core;

        initialisePatientList();
        attachPatientListListener();

        JFXScrollPane.smoothScrolling(scrollPane);
    }

    /**
     * Initialises {@code patientListPanel}.
     */
    private void initialisePatientList() {
        for (Map.Entry<String, Patient> pair : core.patientMap.getPatientObservableMap().entrySet()) {
            patientListPanel.getChildren().add(new PatientCard(pair.getValue()));
        }
    }

    /**
     * Attaches a listener to the patient map.
     * This listener updates the {@code patientListPanel} whenever the patient map is updated.
     */
    private void attachPatientListListener() {
        core.patientMap.getPatientObservableMap().addListener((MapChangeListener<String, Patient>) change -> {
            if (change.wasAdded()) {
                core.ui.print("Patient added.");
                patientListPanel.getChildren().add(new PatientCard(change.getValueAdded()));
            } else if (change.wasRemoved()) {
                core.ui.print("Patient discharged.");
                // TODO: Verify correctness
                patientListPanel.getChildren().remove(new PatientCard(change.getValueRemoved()));

            }
        });
    }
}
