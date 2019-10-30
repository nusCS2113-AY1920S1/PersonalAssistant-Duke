package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ListBox extends HBox {
    @FXML
    private Label indexLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeStartLabel;
    @FXML
    private Label timeEndLabel;
    @FXML
    private Label statusLabel;

    /**
     * Container for booking info.
     * @param index of item
     * @param name name of person booking
     * @param venue place being booked
     * @param date start time
     * @param timeStart start time
     * @param timeEnd end time
     * @param status approve/reject status
     */
    public ListBox(String index, String name, String venue, String date, String timeStart,
                   String timeEnd, String status) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Ui.class.getResource("/view/ListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexLabel.setText(index.toString() + ".");
        nameLabel.setText(name);
        venueLabel.setText(venue);
        dateLabel.setText(date);
        timeStartLabel.setText(timeStart);
        timeEndLabel.setText(timeEnd);
        statusLabel.setText(status);
    }

    public static ListBox getItem(String index, String name, String venue, String date,
        String timeStart, String timeEnd, String status) {
        return new ListBox(index, name, venue, date, timeStart, timeEnd, status);
    }
}
