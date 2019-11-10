package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixException;
import optix.exceptions.OptixInvalidCommandException;
import optix.exceptions.OptixInvalidDateException;
import optix.ui.Ui;
import optix.util.OptixDateFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author CheeSengg
public class ListDateCommand extends Command {
    private final String monthOfYear;
    private String formattedMonthOfYear;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_FOUND_SHOW = "These shows are showing on %1$s: \n";

    private static final String MESSAGE_NO_SHOWS_FOUND = "☹ OOPS!!! There are no shows on %1$s.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ListDateCommand(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        try {
            String[] splitStr = monthOfYear.trim().split(" ");
            if (splitStr.length != 2) {
                throw new OptixInvalidCommandException();
            }
            int year = formatter.getYear(splitStr[1].trim());
            int month = formatter.getMonth(splitStr[0].trim().toLowerCase());
            if (year < storage.getToday().getYear() || month == 0) {
                throw new OptixInvalidDateException();
            }
            formattedMonthOfYear = formatter.intToMonth(month) + ' ' + year;
            LocalDate startOfMonth = formatter.getStartOfMonth(year, month);
            LocalDate endOfMonth = formatter.getEndOfMonth(year, month);
            message.append(String.format(MESSAGE_FOUND_SHOW, formattedMonthOfYear));
            message.append(model.listShow(startOfMonth, endOfMonth));
            if (!hasShow(message.toString())) {
                message = new StringBuilder(String.format(MESSAGE_NO_SHOWS_FOUND, formattedMonthOfYear));
            }
        } catch (OptixException e) {
            message.append(e.getMessage());
            ui.setMessage(message.toString());
            return "";
        }
        ui.setMessage(message.toString());
        return "show";
    }

    /**
     * Dummy Command. Not used
     *
     * @param details n.a
     * @return n.a.
     */
    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }

    private boolean hasShow(String message) {
        return !message.equals(String.format(MESSAGE_FOUND_SHOW, formattedMonthOfYear));
    }

    private void initLogger() {
        LogManager.getLogManager().reset();
        OPTIXLOGGER.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("OptixLogger.log");
            fh.setLevel(Level.FINE);
            OPTIXLOGGER.addHandler(fh);
        } catch (IOException e) {
            OPTIXLOGGER.log(Level.SEVERE, "File logger not working", e);
        }
        OPTIXLOGGER.log(Level.FINEST, "Logging in " + this.getClass().getName());
    }
}
