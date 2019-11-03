package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

//@@author NicholasLiu97
public class ReassignSeatCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

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
        StringBuilder message = new StringBuilder();
        try {
            String[] detailsArray = parseDetails(this.details);
            String showName = detailsArray[0].trim();
            String showDate = detailsArray[1].trim();
            String oldSeat = detailsArray[2].trim();
            String newSeat = detailsArray[3].trim();

            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) { //found the show
                message.append(model.reassignSeat(showLocalDate, oldSeat, newSeat));
                storage.write(model.getShows());
            } else { //no show on the showDate
                message.append(MESSAGE_SHOW_NOT_FOUND);
            }
        } catch (OptixException e) {
            message.append(e.getMessage());
            ui.setMessage(message.toString());
            return "";
        }
        ui.setMessage(message.toString());
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
