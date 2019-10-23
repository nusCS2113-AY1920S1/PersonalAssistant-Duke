package optix.ui.windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import optix.commons.model.Seat;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class SeatsDisplayController extends VBox {
    private Theatre theatre;
    private LocalDate localDate;

    private final OptixDateFormatter formatter = new OptixDateFormatter();

    @FXML
    private HBox rowA;
    @FXML
    private HBox rowB;
    @FXML
    private HBox rowC;
    @FXML
    private HBox rowD;
    @FXML
    private HBox rowE;
    @FXML
    private HBox rowF;
    @FXML
    private Label displayDate;
    @FXML
    private Label displayShowName;

    private SeatsDisplayController(Theatre theatre, LocalDate localDate) {
        this.theatre = theatre;
        this.localDate = localDate;

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/SeatsDisplay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayShowName.setText(theatre.getShowName());
        displayDate.setText(formatter.toStringDate(localDate));
        fillSeats();
    }

    public static SeatsDisplayController displaySeats(Theatre theatre, LocalDate localDate) {
        return new SeatsDisplayController(theatre, localDate);
    }

    private void fillSeats() {
        Seat[][] seats = theatre.getSeats();
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                switch (i) {
                case 0:
                    rowA.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                case 1:
                    rowB.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                case 2:
                    rowC.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                case 3:
                    rowD.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                case 4:
                    rowE.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                case 5:
                    rowF.getChildren().add(SeatController.getSeat(i, j, seats[i][j]));
                    break;
                default:
                    System.out.println("error");
                }
            }
        }
    }
}
