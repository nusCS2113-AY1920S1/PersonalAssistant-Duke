package javacake.commands;

import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.IOException;

public class ChangeColorCommand extends Command {
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) {
        return "Changed color mode!\nType 'list' for more commands\nType 'help' for command info\n";
    }
}
