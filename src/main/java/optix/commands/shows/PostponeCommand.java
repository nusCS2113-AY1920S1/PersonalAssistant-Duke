package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;

import optix.exceptions.OptixInvalidCommandException;

import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class PostponeCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_DOES_NOT_MATCH = "☹ OOPS!!! Did you get the wrong date or wrong show. \n"
            + "Try again!\n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    private static final String MESSAGE_SHOW_CLASH = "☹ OOPS!!! There already exists a show for %1$s.\n";

    private static final String MESSAGE_INVALID_NEW_DATE = "☹ OOPS!!! It is not possible to postpone to the past.\n";

    private static final String MESSAGE_SUCCESSFUL = "%1$s has been postponed from %2$s to %3$s.\n";

    /**
     * Command to postpone show.
     *
     * @param splitStr String containing "SHOW_NAME|OLD_DATE|NEW_DATE"
     */

    public PostponeCommand(String splitStr) {
        this.details = splitStr;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        String showName, oldDate, newDate;
        try {
            String[] details = parseDetails(this.details);
            showName = details[0].trim();
            oldDate = details[1].trim();
            newDate = details[2].trim();
        } catch (OptixInvalidCommandException e) {
            ui.setMessage(e.getMessage());
            return "show";
        }

        String message = "";
        LocalDate today = storage.getToday();

        try {
            if (!formatter.isValidDate(oldDate) || !formatter.isValidDate(newDate)) {
                throw new OptixInvalidDateException();
            }

            LocalDate localOldDate = formatter.toLocalDate(oldDate);
            LocalDate localNewDate = formatter.toLocalDate(newDate);

            if (localNewDate.compareTo(today) <= 0) {
                message = MESSAGE_INVALID_NEW_DATE;
            } else {
                if (!model.containsKey(localOldDate)) {
                    message = MESSAGE_SHOW_NOT_FOUND;
                } else if (model.containsKey(localNewDate)) {
                    message = String.format(MESSAGE_SHOW_CLASH, newDate);
                } else if (!model.hasSameName(localOldDate, showName)) {
                    message = MESSAGE_DOES_NOT_MATCH;
                } else {
                    model.postponeShow(localOldDate, localNewDate);
                    message = String.format(MESSAGE_SUCCESSFUL, showName, oldDate, newDate);
                }
            }
        } catch (OptixInvalidDateException e) {
            message = e.getMessage();
        } finally {
            ui.setMessage(message);
        }
        return "show";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.trim().split("\\|",3);
        if ((detailsArray.length) != 3) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

}
