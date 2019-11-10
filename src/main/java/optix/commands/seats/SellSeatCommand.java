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
public class SellSeatCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
        initLogger();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        OPTIXLOGGER.log(Level.INFO, "executing command");
        StringBuilder message = new StringBuilder();

        try {
            String[] detailsArray = parseDetails(this.details);
            String showName = detailsArray[0].trim();
            String showDate = detailsArray[1].trim();
            String[] seats = detailsArray[2].trim().split(" ");
            for (int i = 0; i < seats.length; i += 1) {
                seats[i] = seats[i].trim();
            }
            if (!formatter.isValidDate(showDate)) {
                OPTIXLOGGER.log(Level.WARNING, "Invalid date given:" + showDate);
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) {
                message.append(model.sellSeats(showLocalDate, seats));
                storage.write(model.getShows());
            } else {
                OPTIXLOGGER.log(Level.WARNING, "Show not found: " + showName);
                ui.setMessage(MESSAGE_SHOW_NOT_FOUND);
                return "";
            }
        } catch (OptixException e) {
            OPTIXLOGGER.log(Level.WARNING, "Error selling seat. Details:" + this.details);
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
        if (detailsArray.length != 3) {
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