package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ListBox extends HBox {
    @FXML
    private Label nameLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label dateStartLabel;
    @FXML
    private Label dateEndLabel;
    @FXML
    private Label statusLabel;

    /**
     * Container for booking info.
     * @param name name of person booking
     * @param venue place being booked
     * @param dateStart start time
     * @param dateEnd end time
     * @param status approve/reject status
     */
    public ListBox(String name, String venue, String dateStart, String dateEnd, String status) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Ui.class.getResource("/view/ListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameLabel.setText(name);
        venueLabel.setText(venue);
        dateStartLabel.setText(dateStart);
        dateEndLabel.setText(dateEnd);
        statusLabel.setText(status);
    }

    public static ListBox getItem(String name, String venue, String dateStart, String dateEnd, String status) {
        return new ListBox(name, venue, dateStart, dateEnd, status);
    }
}
