package duke.gui;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import duke.DukeCore;
import duke.data.Patient;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.Map;

/**
 * UI window for the Home context.
 */
public class HomeWindow extends UiComponent<Region> {
    private static final String FXML = "HomeTab.fxml";
    private final DukeCore core;
    @FXML
    private JFXMasonryPane patientsListView;
    @FXML
    private ScrollPane scrollPane;
    private ObservableMap<String, Patient> map;

    /**
     * Construct HomeWindow object.
     */
    public HomeWindow(DukeCore core) {
        super(FXML);

        this.core = core;
        core.patientMap.getPatientObservableMap().addListener((MapChangeListener<String, Patient>) change -> {
            core.ui.print("New patient added!");

            if (change.wasAdded()) {
                patientsListView.getChildren().add(new PatientCard(change.getValueAdded()));
            } else if (change.wasRemoved()) {
                // TODO: Remove

            }
        });

        // TODO: order of patient's list
        for (Map.Entry<String, Patient> pair : core.patientMap.getPatientObservableMap().entrySet()) {
            patientsListView.getChildren().add(new PatientCard(pair.getValue()));
        }


        JFXScrollPane.smoothScrolling(scrollPane);
    }
}
