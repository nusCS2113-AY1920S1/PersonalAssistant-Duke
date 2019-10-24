package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ReassignSeatCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show %1$s cannot be found on %2$s.\n";

    private static final String MESSAGE_DOES_NOT_MATCH = "☹ OOPS!!! Did you get the wrong date or wrong show. \n"
            + "Try again!\n";

    /**
     * Changes the seat of an existing customer.
     *
     * @param details String of format "SHOW_NAME|SHOW_DATE|OLD_SEAT|NEW_SEAT"
     */
    public ReassignSeatCommand(String details) {
        this.details = details;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        // get details
        String showName, showDate, oldSeat, newSeat;
        try {
            String[] detailsArray = parseDetails(this.details);
            showName = detailsArray[0];
            showDate = detailsArray[1];
            oldSeat = detailsArray[2];
            newSeat = detailsArray[3];
        } catch (OptixInvalidCommandException e) {
            ui.setMessage(e.getMessage());
            return "seat";
        }

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
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.trim().split("\\|");
        if (detailsArray.length != 4) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }
}
