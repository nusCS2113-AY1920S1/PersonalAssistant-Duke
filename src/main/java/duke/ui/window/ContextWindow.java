package duke.ui.window;

import duke.data.DukeObject;
import duke.exception.DukeFatalException;
import duke.ui.commons.UiElement;
import duke.ui.context.UiContext;
import javafx.scene.layout.Region;

import java.util.List;

/**
 * A generic UI window that is associated with a particular {@link UiContext}.
 */
public abstract class ContextWindow extends UiElement<Region> {
    /**
     * Constructs a generic UI context window.
     *
     * @param fxmlFileName Name of FXML file.
     */
    public ContextWindow(String fxmlFileName) {
        super(fxmlFileName, null);
    }

    /**
     * Updates all UI elements in the context window.
     */
    public abstract void updateUi() throws DukeFatalException;

    /* TODO: TEMPORARY */
    /**
     * Retrieves indexed list of {@code DukeObject}.
     *
     * @return Indexed list of DukeObjects.
     */
    public abstract List<DukeObject> getIndexedList(String type);
}
