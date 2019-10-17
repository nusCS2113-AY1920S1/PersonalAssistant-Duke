package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class AddCommand extends Command {
    private String showName;
    private String date;
    private double seatBasePrice;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_IN_THE_PAST = "☹ OOPS!!! It is not possible to perform in the past.\n";

    private static final String MESSAGE_THEATRE_BOOKED = "☹ OOPS!!! There is already a show being added on that date.\n"
            + "Please try again. \n";

    private static final String MESSAGE_SUCCESSFUL = "Got it. I've added this show:\n"
            + "%1$s on %2$s\n";

    /**
     * Add a show to the show list.
     *
     * @param showName      name of new show.
     * @param date          date of new show.
     * @param seatBasePrice the base price of the seat.
     */
    public AddCommand(String showName, String date, double seatBasePrice) {
        // need to check if it is a valid date if not need to throw exception
        this.showName = showName;
        this.date = date;
        this.seatBasePrice = seatBasePrice;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        LocalDate today = storage.getToday();

        try {
            if (!formatter.isValidDate(date)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(date);

            if (showLocalDate.compareTo(today) <= 0) {
                ui.setMessage(MESSAGE_IN_THE_PAST);
            } else if (model.containsKey(showLocalDate)) {
                ui.setMessage(MESSAGE_THEATRE_BOOKED);
            } else {
                model.addShow(showName, showLocalDate, seatBasePrice);
                ui.setMessage(String.format(MESSAGE_SUCCESSFUL, showName, date));
            }
        } catch (OptixInvalidDateException e) {
            ui.setMessage(e.getMessage());
        }
    }


    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
