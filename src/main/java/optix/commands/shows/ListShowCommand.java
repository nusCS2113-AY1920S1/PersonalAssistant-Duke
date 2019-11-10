package optix.commands.shows;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author CheeSengg
public class ListShowCommand extends Command {
    private String showName;

    private static final String MESSAGE_FOUND_SHOW = "The show %1$s is showing on the following following dates: \n";

    private static final String MESSAGE_SHOW_NOT_FOUND = "☹ OOPS!!! The show cannot be found.\n";
    private static final Logger OPTIXLOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ListShowCommand(String showName) {
        this.showName = showName.trim();
    }

    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder(String.format(MESSAGE_FOUND_SHOW, showName));

        message.append(model.listShow(showName));
        if (!hasShow(message.toString())) {
            message = new StringBuilder(MESSAGE_SHOW_NOT_FOUND);
        }
        ui.setMessage(message.toString());
        return "show";
    }

    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }

    private boolean hasShow(String message) {
        return !message.equals(String.format(MESSAGE_FOUND_SHOW, showName));
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
