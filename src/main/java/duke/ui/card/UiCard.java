package duke.ui.card;

import duke.DukeCore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * An UI element that displays basic information of a {@code DukeObject}.
 */
public abstract class UiCard extends AnchorPane {
    private static final String FXML_FILE_FOLDER = "/view/";

    @FXML
    private Label indexLabel;

    private int index;

    /**
     * Constructs a UiCard object with the specified {@code DukeObject}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     * @param index        Displayed index.
     */
    public UiCard(String fxmlFileName, int index) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource(FXML_FILE_FOLDER + fxmlFileName));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO: Proper exception handling here.
            throw new AssertionError(e);
        }

        this.index = index;
        indexLabel.setText(indexLabel.getText() + " " + index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        return object == this;
    }

    public int getIndex() {
        return index;
    }
}
