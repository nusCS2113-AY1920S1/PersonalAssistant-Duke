package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;

import optix.exceptions.OptixInvalidCommandException;

import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class SellSeatCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    /**
     * Instantiates the command.
     * This function is called when the customer has already
     * decided on his seat.
     *
     * @param splitStr String in the format "SHOW_NAME|SHOW_DATE|DATE_1 DATE_2 etc."
     */
    public SellSeatCommand(String splitStr) {
        this.details = splitStr;
    }

    //need to refactor
    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        // get details
        String showName, showDate;
        String[] seats;
        try {
            String[] detailsArray = parseDetails(this.details);
            showName = detailsArray[0];
            showDate = detailsArray[1];
            seats = detailsArray[2].split(" ");
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

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.trim().split("\\|");
        if (details.length() != 3) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

}