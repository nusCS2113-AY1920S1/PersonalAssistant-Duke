package ui;

import exception.DukeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

//@@author AmosChan97
public class RoomListBox extends HBox {

    @FXML
    private Label indexLabel;

    @FXML
    private Label roomLabel;

    @FXML
    private Label capacityLabel;

    /**
     * For displaying room related information.
     * @param index Index of room in the list
     * @param room room code
     * @param capacity number of people able to use the room at the same time
     * @throws DukeException when input does not exist or invalid
     */
    public RoomListBox(String index, String room, String capacity) throws DukeException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Ui.class.getResource("/view/RoomListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new DukeException("RoomListBox not available");
        }
        indexLabel.setText(index + ".");
        roomLabel.setText(room);
        capacityLabel.setText(capacity);
    }

}

