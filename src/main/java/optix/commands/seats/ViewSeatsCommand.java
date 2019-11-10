package optix.commands.seats;

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
public class ViewSeatsCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_FOUND = "Here is the layout of the theatre for %1$s on %2$s:\n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! Sorry the show %1$s cannot be found.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Command to view seats of a show.
     *
     * @param splitStr String of format "SHOW_NAME|SHOW_DATE"
     */
    public ViewSeatsCommand(String splitStr) {
        this.details = splitStr;
        initLogger();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        OPTIXLOGGER.log(Level.INFO, "executing command");
        StringBuilder message = new StringBuilder();
        try {
            String[] arrayDetails = parseDetails(this.details);
            String showName = arrayDetails[0].trim();
            String showDate = arrayDetails[1].trim();

            if (!formatter.isValidDate(showDate)) {
                OPTIXLOGGER.log(Level.WARNING, "Invalid date given:" + showDate);
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                message = new StringBuilder(String.format(MESSAGE_SHOW_FOUND, showName, showDate));
                message.append(model.viewSeats(showLocalDate));
            } else {
                OPTIXLOGGER.log(Level.WARNING, "Show not found: " + showName);
                ui.setMessage(String.format(MESSAGE_SHOW_NOT_FOUND, showName));
                return "";
            }
        } catch (OptixException e) {
            OPTIXLOGGER.log(Level.WARNING, "Error viewing seat. Details:" + this.details);
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
        if (detailsArray.length != 2) {
            throw new OptixInvalidCommandException();
        }
        return detailsArray;
    }

    private void initLogger() {
        LogManager.getLogManager().reset();
        OPTIXLOGGER.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("OptixLogger.log", true);
            fh.setLevel(Level.FINE);
            OPTIXLOGGER.addHandler(fh);
        } catch (IOException e) {
            OPTIXLOGGER.log(Level.SEVERE, "File logger not working", e);
        }
        OPTIXLOGGER.log(Level.FINEST, "Logging in " + this.getClass().getName());
    }
}
