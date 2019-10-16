package optix.commands.seats;

import optix.commons.Model;
import optix.ui.Ui;
import optix.commands.Command;
import optix.commons.Storage;
import optix.commons.model.Theatre;
import optix.exceptions.OptixInvalidDateException;
import optix.util.OptixDateFormatter;
import optix.commons.model.ShowMap;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class SellSeatCommand extends Command {
    private String showName;
    private String showDate;
    private String[] seats;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    /**
     * instantiates the command.
     * This function is called when the customer is
     * interested in viewing the available seats
     *
     * @param showName  name of show
     * @param showDate  date of show
     */
    public SellSeatCommand(String showName, String showDate) {
        this.showName = showName;
        this.showDate = showDate;
        seats = new String[0];
    }

    /**
     * Instantiates the command.
     * This function is called when the customer has already
     * decided on his seat.
     *
     * @param showName  name of show.
     * @param showDate  date of show.
     * @param seats     desired seat.
     */
    public SellSeatCommand(String showName, String showDate, String seats) {
        this.showName = showName;
        this.showDate = showDate;
        this.seats = seats.split(" ");
    }

    //need to refactor
    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        ShowMap shows = model.getShows();
        StringBuilder message = new StringBuilder();
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (shows.containsKey(showLocalDate) && shows.get(showLocalDate).hasSameName(showName)) {
                Theatre show = shows.get(showLocalDate);

                if (seats.length == 0) {
                    new ViewSeatsCommand(showName, showDate).execute(model, ui, storage);
                    System.out.println(ui.showCommandLine());
                    message.append(querySeats(ui, show));
                } else {
                    message.append(show.sellSeats(seats));
                }

            } else {
                message = new StringBuilder(MESSAGE_SHOW_NOT_FOUND);
            }

        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            model.setShows(shows);
            ui.setMessage(message.toString());
        }
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
            double costOfSeat = show.sellSeats(seatInput.trim());

            if (costOfSeat != 0) {
                totalCost += costOfSeat;
                seatsSold.add(seatInput);
                ui.setMessage("Purchase of " + seatInput + " was successful.\n");
            } else {
                ui.setMessage("☹ OOPS!!! Purchase of " + seatInput + " was unsuccessful.\n");
            }
            System.out.println(ui.showCommandLine());
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
