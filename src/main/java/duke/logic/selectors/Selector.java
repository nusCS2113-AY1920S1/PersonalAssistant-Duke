package duke.logic.selectors;

import javafx.scene.input.KeyEvent;

/**
 * The API of selectors.
 */
public interface Selector {
    int getIndex();

    void feedKeyEvent(KeyEvent keyEvent);
}
