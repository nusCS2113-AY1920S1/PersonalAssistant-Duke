package duke.ui.card;

import duke.DukeCore;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI element that displays basic information of a {@code DukeObject}.
 */
public abstract class UiCard extends AnchorPane {
    private static final String FXML_FILE_FOLDER = "/view/";

    /**
     * Constructs a UiCard object with the specified {@code DukeObject}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     */
    public UiCard(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource(FXML_FILE_FOLDER + fxmlFileName));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO: Proper exception handling here.
            throw new AssertionError(e);
        }
    }
}
