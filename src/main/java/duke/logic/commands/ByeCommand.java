package duke.logic.commands;

import duke.log.Log;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command for exiting SpongeBob.
 */
public class ByeCommand extends Command {
    public static final String COMMAND_WORD = "bye";
    private static final Logger logger = Log.getLogger();
    private static final String LOG_FOR_EXIT_COMMAND = " Executing command for exiting SpongeBob";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {
        logger.log(Level.INFO, LOG_FOR_EXIT_COMMAND);
        this.isExit = true;
        ui.exitSpongeBob();
    }
}
