package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SellSeatCommand extends Command {
    private String showName;
    private String showDate;
    private String[] seats;
    private String buyerName;

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

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        LocalDate key = toLocalDate(showDate);
        if (!shows.isEmpty() && shows.containsKey(key) && shows.get(key).hasSameName(showName)) {
            Theatre show = shows.get(key);
            if (seats.length == 0) {
                new ViewSeatsCommand(showName, showDate).execute(shows, ui, storage);
                System.out.println(ui.showLine());
                message.append(querySeats(ui, show));
            } else {
                message.append(show.sellSeats(buyerName, seats));
            }
        } else {
            message = new StringBuilder("Sorry the show you are looking for does not exist");
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }

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

    private String getFormat(String date) {
        int padCount = 0;

        StringBuilder format = new StringBuilder();
        String[] timeType = {"d","M","y","H","H","m","m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format.append(timeType[padCount]);
                if (padCount >= 3) { padCount += 1;}
            } else {
                format.append(c);
                padCount += 1;
            }
        }
        return format.toString();

    }

    //TODO create a date formatter class
    /**
     * function to convert String to localDate

     * note that currently the format is fixed 1/1/1997
     * @param dateString
     * @return
     */
    private LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(dateString));
        //Convert string to localdate
        LocalDate localDate = LocalDate.parse(dateString,formatter);
        return localDate;
    }
}
