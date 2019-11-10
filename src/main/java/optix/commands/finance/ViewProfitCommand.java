package optix.commands.finance;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.commons.model.ShowMap;
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
public class ViewProfitCommand extends Command {
    private String details;

    private OptixDateFormatter formatter = new OptixDateFormatter();

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";

    private static final String MESSAGE_SUCCESSFUL = "The profit for %1$s on %2$s is $%3$.2f\n";

    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Views the profit made from a show on a certain date.
     *
     * @param splitStr String of format "SHOW_NAME|SHOW_DATE"
     */
    public ViewProfitCommand(String splitStr) {
        this.details = splitStr;
        initLogger();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        OPTIXLOGGER.log(Level.INFO, "executing command");
        StringBuilder message = new StringBuilder();
        String showName;
        String showDate;
        try {
            String[] detailsArray = parseDetails(this.details);
            showName = detailsArray[0].trim();
            showDate = detailsArray[1].trim();

            if (!formatter.isValidDate(showDate)) {
                OPTIXLOGGER.log(Level.WARNING, "invalid date provided");
                throw new OptixInvalidDateException();
            }

            LocalDate localDate = formatter.toLocalDate(showDate);

            if (localDate.compareTo(storage.getToday()) <= 0) { //in archive list
                ShowMap showsHistory = model.getShowsHistory();
                if (showsHistory.containsKey(localDate) && showsHistory.get(localDate).hasSameName(showName)) { //date not found
                    message.append(String.format(MESSAGE_SUCCESSFUL, showName, showDate,
                            showsHistory.getProfit(localDate)));
                } else {
                    OPTIXLOGGER.log(Level.WARNING, "Show not found");
                    message.append(MESSAGE_SHOW_NOT_FOUND);
                }
            } else {
                ShowMap shows = model.getShows();
                if (shows.containsKey(localDate) && model.hasSameName(localDate, showName)) {
                    message.append(String.format(MESSAGE_SUCCESSFUL, showName, showDate,
                            shows.getProfit(localDate)));
                } else {
                    OPTIXLOGGER.log(Level.WARNING, "Show not found");
                    message.append(MESSAGE_SHOW_NOT_FOUND);
                }
            }
        } catch (OptixException e) {
            message.append(e.getMessage());
            ui.setMessage(message.toString());
            return "";
        }
        ui.setMessage(message.toString());
        return "finance";
    }

    @Override
    public String[] parseDetails(String details) throws OptixInvalidCommandException {
        String[] detailsArray = details.trim().split("\\|");
        if (detailsArray.length != 2) {
            OPTIXLOGGER.log(Level.WARNING, "Error parsing command details");
            OPTIXLOGGER.log(Level.WARNING, "Expected details array length: 2");
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
