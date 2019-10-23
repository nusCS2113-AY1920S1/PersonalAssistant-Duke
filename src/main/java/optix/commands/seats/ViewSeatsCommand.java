package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ViewSeatsCommand extends Command {
    private String showName;
    private String showDate;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_FOUND = "Here is the layout of the theatre for %1$s on %2$s:\n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! Sorry the show %1$s cannot be found.\n";

    /**
     * Command to view seats of a show.
     * @param splitStr String of format "SHOW_NAME|SHOW_DATE"
     * @throws OptixInvalidCommandException When number of arguments are not correct (must be 2 arguments)
     */
    public ViewSeatsCommand(String splitStr) throws OptixInvalidCommandException {
        String[] details = parseDetails(splitStr);
        if (details.length != 2) {
            throw new OptixInvalidCommandException();
        }
        this.showName = details[0].trim();
        this.showDate = details[1].trim();
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder(String.format(MESSAGE_SHOW_FOUND, showName, showDate));
        try {
            if (!formatter.isValidDate(showDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                message.append(model.viewSeats(showLocalDate));
            } else {
                message = new StringBuilder(String.format(MESSAGE_SHOW_NOT_FOUND, showName));
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
