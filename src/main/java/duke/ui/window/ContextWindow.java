package duke.ui.window;

import duke.exception.DukeFatalException;
import duke.ui.commons.UiElement;
import duke.ui.context.UiContext;
import javafx.scene.layout.Region;

//@@author gowgos5
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
     *
     * @throws DukeFatalException If the {@link ContextWindow} to be updated cannot be loaded / initialised.
     */
    public abstract void updateUi() throws DukeFatalException;
}
