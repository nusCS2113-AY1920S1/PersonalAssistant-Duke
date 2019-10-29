package optix.ui.windows;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import optix.commons.model.Theatre;
import optix.util.OptixDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class FinanceController extends AnchorPane {
    private Theatre theatre;
    private LocalDate date;

    private final OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_PROFIT = "$%1$.2f";

    @FXML
    private Label displayDate;
    @FXML
    private Label displayShowName;
    @FXML
    private Label displayRevenue;


    private FinanceController(Theatre theatre, LocalDate date) {
        this.theatre = theatre;
        this.date = date;

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/FinanceEntry.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        displayShowName.setText(theatre.getShowName());
        displayDate.setText(formatter.toStringDate(date));
        displayRevenue.setText(showRevenue());
    }

    private String showRevenue() {
        return String.format(MESSAGE_PROFIT, theatre.getProfit());
    }

    public static FinanceController displayFinance(Theatre theatre, LocalDate date) {
        return new FinanceController(theatre, date);
    }
}
