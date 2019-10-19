package duke.gui;

import javafx.scene.layout.Region;

/**
 * UI window for the Patient context.
 */
public class PatientWindow extends UiElement<Region> {
    private static final String FXML = "PatientTab.fxml";

    /**
     * Construct Patient object.
     */
    public PatientWindow() {
        super(FXML, null);
    }
}
