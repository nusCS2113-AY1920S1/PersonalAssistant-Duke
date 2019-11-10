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

//@@author NicholasLiu97
public class ReassignSeatCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Changes the seat of an existing customer.
     *
     * @param details String of format "SHOW_NAME|SHOW_DATE|OLD_SEAT|NEW_SEAT"
     */
    public ReassignSeatCommand(String details) {
        this.details = details;
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
            String oldSeat = detailsArray[2].trim();
            String newSeat = detailsArray[3].trim();

            if (!formatter.isValidDate(showDate)) {
                OPTIXLOGGER.log(Level.WARNING, "Error with showDate:" + showDate);
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(showDate);

            if (model.containsKey(showLocalDate) && model.hasSameName(showLocalDate, showName)) { //found the show
                message.append(model.reassignSeat(showLocalDate, oldSeat, newSeat));
                storage.write(model.getShows());
            } else { //no show on the showDate
                OPTIXLOGGER.log(Level.WARNING, "Show not found: " + showName);
                ui.setMessage(MESSAGE_SHOW_NOT_FOUND);
                return "";
            }
        } catch (OptixException e) {
            OPTIXLOGGER.log(Level.WARNING, "Error reassigning seat. Details:" + this.details);
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
        if (detailsArray.length != 4) {
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
