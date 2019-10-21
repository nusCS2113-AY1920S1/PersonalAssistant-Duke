package optix.ui.windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import optix.commons.model.Seat;

import java.io.IOException;

public class SeatController extends StackPane {
    private Seat seat;
    private int row;
    private int col;
    private String seatNumber;

    @FXML
    private Label labelNumber;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Circle circle;

    private SeatController(int row, int col, Seat seat) {
        FXMLLoader fxmlLoader = new FXMLLoader(SeatsDisplayController.class.getResource("/view/Seat.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.seat = seat;
        seatNumber = getRow(row) + getCol(col);
        labelNumber.setText(seatNumber);

        if (seat.isBooked()) {
            setBooked();
        }
    }

    public static SeatController getSeat(int row, int col, Seat seat) {
        return new SeatController(row, col, seat);
    }

    private String getRow(int row) {
        switch (row) {
        case 0:
            return "A";
        case 1:
            return "B";
        case 2:
            return "C";
        case 3:
            return "D";
        case 4:
            return "E";
        case 5:
            return "F";
        default:
            return "error";
        }
    }

    private String getCol(int col) {
        return Integer.toString(col + 1);
    }

    private void setBooked() {
        changeColor(Color.web("#CB4335"));
    }

    private void changeColor(Color color) {
        rectangle.setFill(color);
        circle.setFill(color);
    }
}
