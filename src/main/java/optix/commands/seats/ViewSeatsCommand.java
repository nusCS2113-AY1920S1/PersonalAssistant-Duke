package optix.commands.seats;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
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

    public ViewSeatsCommand(String showName, String showDate) {
        this.showName = showName;
        this.showDate = showDate;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
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
        return "seat";
    }
}
