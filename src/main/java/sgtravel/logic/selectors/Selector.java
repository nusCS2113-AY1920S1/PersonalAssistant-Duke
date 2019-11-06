package sgtravel.logic.selectors;

import javafx.scene.input.KeyCode;

/**
 * The API of selectors.
 */
public interface Selector {
    int getIndex();

    void feedKeyCode(KeyCode keyCode);
}
