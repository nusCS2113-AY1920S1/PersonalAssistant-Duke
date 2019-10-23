package optix.ui.windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class ShowController extends AnchorPane {
    private Theatre theatre;
    private LocalDate date;

    private final OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SEATS_AVAILABILITY = "%1$s (Price: $%2$.2f)";

    @FXML
    private Label displayDate;
    @FXML
    private Label displayShowName;
    @FXML
    private Label displayTier1Seats;
    @FXML
    private Label displayTier2Seats;
    @FXML
    private Label displayTier3Seats;

    private ShowController(Theatre theatre, LocalDate date) {
        this.theatre = theatre;
        this.date = date;

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ShowEntry.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayShowName.setText(theatre.getShowName());
        displayDate.setText(formatter.toStringDate(date));
        displayTier1Seats.setText(seatsAvailability(theatre.getTierOneSeats(), theatre.getSeatBasePrice()));
        displayTier2Seats.setText(seatsAvailability(theatre.getTierTwoSeats(), theatre.getSeatBasePrice() * 1.2));
        displayTier3Seats.setText(seatsAvailability(theatre.getTierThreeSeats(), theatre.getSeatBasePrice() * 1.5));
    }

    private String seatsAvailability(String seatsLeft, double seatPrice) {
        return String.format(MESSAGE_SEATS_AVAILABILITY, seatsLeft, seatPrice);
    }

    public static ShowController displayShow(Theatre theatre, LocalDate date) {
        return new ShowController(theatre, date);
    }
}
