package duke.ui.window;

import duke.data.DukeObject;
import duke.ui.UiElement;
import javafx.scene.layout.Region;

import java.util.List;

public abstract class ContextWindow extends UiElement<Region> {
    /**
     * Constructs a generic UI window.
     *
     * @param fxmlFileName Name of FXML file.
     */
    public ContextWindow(String fxmlFileName) {
        super(fxmlFileName, null);
    }

    /**
     * Update UI window.
     */
    public abstract void updateUi();

    /**
     * Retrieves indexed list of {@code DukeObject}.
     *
     * @return Indexed list of DukeObjects.
     */
    public abstract List<DukeObject> getIndexedList(String type);
}
