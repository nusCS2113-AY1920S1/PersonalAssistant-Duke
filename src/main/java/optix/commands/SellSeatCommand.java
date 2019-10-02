package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.OptixDateFormatter;
import optix.util.ShowMap;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SellSeatCommand extends Command {
    private String showName;
    private String showDate;
    private String[] seats;
    private String buyerName;

    private OptixResponse response = new OptixResponse();
    private OptixDateFormatter formatter = new OptixDateFormatter();

    public SellSeatCommand(String showName, String showDate, String buyerName) {
        this.showName = showName;
        this.showDate = showDate;
        this.buyerName = buyerName;
        seats = new String[0];
    }

    public SellSeatCommand(String showName, String showDate, String buyerName, String seats) {
        this.showName = showName;
        this.showDate = showDate;
        this.buyerName = buyerName;
        this.seats = seats.split(" ");
    }

    //need to refactor
    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        LocalDate showLocalDate = formatter.toLocalDate(showDate);
        if (!shows.isEmpty() && shows.containsKey(showLocalDate) && shows.get(showLocalDate).hasSameName(showName)) {
            Theatre show = shows.get(showLocalDate);
            if (seats.length == 0) {
                new ViewSeatsCommand(showName, showDate).execute(shows, ui, storage);
                System.out.println(ui.showLine());
                message.append(querySeats(ui, show));
            } else {
                message.append(show.sellSeats(buyerName, seats));
            }
        } else {
            message = new StringBuilder(response.SHOW_NOT_FOUND);
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }

    //need to refactor
    private String querySeats(Ui ui, Theatre show) {
        String seatInput = "";
        String message;
        ArrayList<String> seatsSold = new ArrayList<>();
        double totalCost = 0;

        while (true) {
            System.out.println("Key in your preferred seat: ");
            seatInput = ui.readCommand();

            if (seatInput.trim().toLowerCase().equals("done")) {
                break;
            }

            // TODO: Bug fix for seatInput query. If deviated from seat input, error will occur.
            double costOfSeat = show.sellSeats(buyerName, seatInput);

            if (costOfSeat != 0) {
                totalCost += costOfSeat;
                seatsSold.add(seatInput);
                ui.setMessage("Purchase of " + seatInput + " was successful.\n");
            } else {
                ui.setMessage("☹ OOPS!!! Purchase of " + seatInput + " was unsuccessful.\n");
            }
            System.out.println(ui.showLine());
        }

        if (!seatsSold.isEmpty()) {
            message = "You have successfully purchased the following seats: \n"
                    + seatsSold + "\n"
                    + "The total cost of the ticket is " + new DecimalFormat("$#.00").format(totalCost) + "\n";
        } else {
            message = "No Seats were purchased";
        }

        return message;
    }
}
