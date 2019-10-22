package duke.ui;

import javafx.scene.layout.Region;

/**
 * UI window for the Patient context.
 */
class PatientWindow extends UiElement<Region> {
    private static final String FXML = "PatientWindow.fxml";

    /**
     * Constructs the patient UI window.
     */
    PatientWindow() {
        super(FXML, null);
    }
}
