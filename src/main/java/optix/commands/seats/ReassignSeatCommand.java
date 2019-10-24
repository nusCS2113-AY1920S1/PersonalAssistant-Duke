package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ReassignSeatCommand extends Command {

    private String showName;
    private String showDate;
    private String oldSeat;
    private String newSeat;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show %1$s cannot be found on %2$s.\n";

    private static final String MESSAGE_DOES_NOT_MATCH = "☹ OOPS!!! Did you get the wrong date or wrong show. \n"
            + "Try again!\n";

    /**
     * Changes the seat of an existing customer.
     *
     * @param showName name of show.
     * @param showDate date of show.
     * @param oldSeat  seat to be re-assigned.
     * @param newSeat  new seat.
     */
    public ReassignSeatCommand(String showName, String showDate, String oldSeat, String newSeat) {
        this.showName = showName;
        this.showDate = showDate;
        this.oldSeat = oldSeat;
        this.newSeat = newSeat;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) { //found the show
                message.append(model.reassignSeat(showLocalDate, oldSeat, newSeat));
            } else if (model.containsKey(showLocalDate)) { //no show on the showDate
                message.append(String.format(MESSAGE_SHOW_NOT_FOUND, showName, showDate));
            } else { //date does not exist in Optix
                message.append(MESSAGE_DOES_NOT_MATCH);
            }
        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
        return "seat";
    }

    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }
}
