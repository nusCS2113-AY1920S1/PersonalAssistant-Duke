package javacake.commands;

import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.IOException;

public class ResetCommand extends Command {
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        return "Confirm reset and deletion of current Profile?\nType 'yes' to confirm\n"
                + "Type anything else to cancel\n";
    }
}
