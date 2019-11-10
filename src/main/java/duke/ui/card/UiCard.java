package duke.ui.card;

import duke.DukeCore;
import duke.exception.DukeFatalException;
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

    private String cardType;

    /**
     * Constructs a UiCard object with the specified {@code DukeObject}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     */
    UiCard(String fxmlFileName) throws DukeFatalException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DukeCore.class.getResource(FXML_FILE_FOLDER + fxmlFileName));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DukeFatalException("FXML files missing, please save your patient data and reinstall the program");
        }

        if (indexLabel != null) {
            cardType = indexLabel.getText();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        return object == this;
    }

    /**
     * Set index of card.
     *
     * @param index New index.
     */
    public void setIndex(int index) {
        indexLabel.setText("[" + index + "] " + cardType);
    }
}
