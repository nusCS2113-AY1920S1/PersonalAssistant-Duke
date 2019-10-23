package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.time.LocalDate;

public class ListDateCommand extends Command {
    private final String monthOfYear;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_FOUND_SHOW = "These shows are showing on %1$s: \n";

    private static final String MESSAGE_NO_SHOWS_FOUND = "☹ OOPS!!! There are no shows on %1$s.\n";

    public ListDateCommand(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    @Override
    public void execute(Model model, Ui ui, Storage storage) {
        String[] splitStr = monthOfYear.split(" ");

        int year = getYear(splitStr[1]);
        int month = getMonth(splitStr[0].toLowerCase());

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

    @Override
    public boolean isExit() {
        return super.isExit();
    }

    private boolean hasShow(String message) {
        return !message.equals(String.format(MESSAGE_FOUND_SHOW, monthOfYear));
    }

    private int getYear(String year) {
        try {
            return Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int getMonth(String month) {
        switch (month) {
        case "january":
            return 1;
        case "february":
            return 2;
        case "march":
            return 3;
        case "april":
            return 4;
        case "may":
            return 5;
        case "june":
            return 6;
        case "july":
            return 7;
        case "august":
            return 8;
        case "september":
            return 9;
        case "october":
            return 10;
        case "november":
            return 11;
        case "december":
            return 12;
        default:
            return 0;
        }
    }
}
