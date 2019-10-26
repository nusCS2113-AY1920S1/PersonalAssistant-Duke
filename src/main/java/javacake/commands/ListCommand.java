package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.Logic;
import javacake.storage.Profile;
import javacake.ui.Ui;
import javacake.storage.Storage;

public class ListCommand extends Command {
    public ListCommand() {
        type = CmdType.LIST;
    }

    /**
     * Execute the listing of current tasks on the Ui.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     */
    @Override
    public String execute(Logic logic, Ui ui, Storage storage, Profile profile) throws DukeException {
        logic.setDefaultFilePath();
        return (logic.processQueries());
    }
}
