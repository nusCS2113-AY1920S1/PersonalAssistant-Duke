package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

//@@author CheeSengg
public class ListDateCommand extends Command {
    private final String monthOfYear;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_FOUND_SHOW = "These shows are showing on %1$s: \n";

    private static final String MESSAGE_NO_SHOWS_FOUND = "☹ OOPS!!! There are no shows on %1$s.\n";

    public ListDateCommand(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        String[] splitStr = monthOfYear.trim().split(" ");
        int year = formatter.getYear(splitStr[1].trim());
        int month = formatter.getMonth(splitStr[0].trim().toLowerCase());

        StringBuilder message = new StringBuilder(String.format(MESSAGE_FOUND_SHOW, monthOfYear));

        try {
            if (year == 0 || month == 0) {
                throw new OptixInvalidDateException();
            }

            LocalDate startOfMonth = formatter.getStartOfMonth(year, month);
            LocalDate endOfMonth = formatter.getEndOfMonth(year, month);

            message.append(model.listShow(startOfMonth, endOfMonth));
            if (!hasShow(message.toString())) {
                message = new StringBuilder(String.format(MESSAGE_NO_SHOWS_FOUND, monthOfYear));
            }
        } catch (OptixInvalidDateException e) {
            message.append(e.getMessage());
        } finally {
            ui.setMessage(message.toString());
        }
        return "show";
    }

    /**
     * Dummy Command. Not used
     * @param details n.a
     * @return n.a.
     */
    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }


    private boolean hasShow(String message) {
        return !message.equals(String.format(MESSAGE_FOUND_SHOW, monthOfYear));
    }


}
