package ui;

import exception.DukeException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class RoomListBox extends HBox {

    @FXML
    private Label indexLabel;

    @FXML
    private Label roomLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeStartLabel;

    @FXML
    private Label timeEndLabel;

    /**
     *
     * @param index
     * @param room
     * @param date
     * @param timeStart
     * @param timeEnd
     * @throws DukeException
     */
    public RoomListBox(String index, String room, String date, String timeStart, String timeEnd) throws DukeException {
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
        dateLabel.setText(date);
        timeStartLabel.setText(timeStart);
        timeEndLabel.setText(timeEnd);
    }

}

