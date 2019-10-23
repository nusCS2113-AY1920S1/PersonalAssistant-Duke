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
     * @param splitStr String in the format "SHOW_NAME|SHOW_DATE|DATE_1 DATE_2 etc."
     */
    public SellSeatCommand(String splitStr) throws OptixInvalidCommandException {
        String[] details = parseDetails(splitStr);
        if (details.length != 3) {
            throw new OptixInvalidCommandException();
        }
        this.showName = details[0];
        this.showDate = details[1];
        this.seats = details[2].split(" ");
    }

    //need to refactor
    @Override
    public void execute(Model model, Ui ui, Storage storage) {
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
    }

    @Override
    public String[] parseDetails(String details) {
        return details.trim().split("\\|");
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}