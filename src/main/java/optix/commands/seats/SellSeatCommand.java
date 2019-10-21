package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class SellSeatCommand extends Command {
    private String showName;
    private String showDate;
    private String[] seats;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

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
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                message.append(model.sellSeats(showLocalDate, seats));
            } else {
                message = new StringBuilder(MESSAGE_SHOW_NOT_FOUND);
            }

        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
        return "seat";
    }
}
